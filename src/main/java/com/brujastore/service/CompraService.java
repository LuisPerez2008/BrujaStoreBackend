package com.brujastore.service;

import com.brujastore.dto.ReporteVentasDTO;
import com.brujastore.entity.Compra;
import com.brujastore.entity.Pedido;
import com.brujastore.repository.CompraRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    // En CompraService.java

    @Transactional
    public Compra save(Compra compra) {
        // Simplemente guarda el objeto Compra que viene del controlador.
        return compraRepository.save(compra);
    }

    @Transactional(readOnly = true)
    public List<Compra> findAll() {
        return compraRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Compra> findById(Long id) {
        return compraRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Compra> findByUsuarioId(Long usuarioId) {
        return compraRepository.findByUsuarioIdOrderByFechaDesc(usuarioId);
    }

    @Transactional
    public void deleteById(Long id) {
        compraRepository.deleteById(id);
    }

    @Transactional
    public Optional<Compra> update(Long id, Compra compraDetails) {
        return compraRepository.findById(id)
                .map(compraExistente -> {
                    compraExistente.setUsuario(compraDetails.getUsuario());
                    compraExistente.setPromocion(compraDetails.getPromocion());
                    compraExistente.setPrecioFinal(compraDetails.getPrecioFinal());
                    compraExistente.setEstado(compraDetails.getEstado());
                    return compraRepository.save(compraExistente);
                });
    }




    @Transactional(readOnly = true)
    public List<ReporteVentasDTO> getVentasDiariasUltimoMes() {
        LocalDateTime fechaFin = LocalDateTime.now();
        LocalDateTime fechaInicio = fechaFin.minusDays(30);
        return compraRepository.findVentasDiarias(fechaInicio, fechaFin);
    }


    @Transactional(readOnly = true)
    public List<ReporteVentasDTO> getVentasMensualesUltimoAno() {
        LocalDateTime fechaFin = LocalDateTime.now();
        LocalDateTime fechaInicio = fechaFin.minusYears(1).with(TemporalAdjusters.firstDayOfMonth());

        // 1. Obtenemos la lista de arrays de objetos del repositorio
        List<Object[]> resultados = compraRepository.findVentasMensuales(fechaInicio, fechaFin);

        // 2. Creamos la lista que vamos a devolver
        List<ReporteVentasDTO> reporte = new ArrayList<>();

        // 3. Recorremos los resultados y los convertimos a DTOs
        for (Object[] resultado : resultados) {
            Integer anio = (Integer) resultado[0];
            Integer mes = (Integer) resultado[1];
            Double totalVentas = (Double) resultado[2];

            // Formateamos el mes para que tenga un cero si es necesario (ej. 7 -> "07")
            String mesFormateado = String.format("%02d", mes);
            String periodo = anio + "-" + mesFormateado;

            // Creamos el DTO y lo añadimos a la lista
            reporte.add(new ReporteVentasDTO(periodo, totalVentas));
        }

        return reporte;
    }


    @Transactional
    public Optional<Compra> actualizarEstado(Long id, String nuevoEstado) {
        return compraRepository.findById(id)
                .map(compraExistente -> {
                    // Guarda el estado anterior antes de modificarlo
                    String estadoAnterior = compraExistente.getEstado();

                    // Actualiza y guarda la entidad
                    compraExistente.setEstado(nuevoEstado);
                    Compra compraActualizada = compraRepository.save(compraExistente);

                    // Llama al método de envío de correo con ambos estados
                    enviarCorreoHtmlDeActualizacion(compraActualizada, estadoAnterior);

                    return compraActualizada;
                });
    }

    private void enviarCorreoHtmlDeActualizacion(Compra compra, String estadoAnterior) {
        // --- INICIO DE LA CORRECCIÓN ---

        // CORRECTO: Usamos getUsuario() para acceder al objeto de usuario
        // y getCorreo() para obtener su dirección de correo.
        String destinatario = compra.getUsuario().getCorreo();
        String nombreCliente = compra.getUsuario().getNombre();

        // --- FIN DE LA CORRECCIÓN ---
        if (compra.getUsuario() == null || compra.getUsuario().getCorreo() == null) {
            System.err.println("⚠️ Usuario o correo no disponible, no se envió el correo.");
            return;
        }

        String idCompra = compra.getId().toString();
        String nuevoEstado = compra.getEstado();

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("luis.08.2044@gmail.com");
            helper.setTo(destinatario);
            helper.setSubject("Actualización del estado de tu compra #" + idCompra);

            // La plantilla HTML se mantiene igual
            String htmlTemplate = """
                <!DOCTYPE html>
                <html>
                <head>
                  <meta charset="UTF-8">
                  <title>Estado de tu compra</title>
                </head>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                  <table width="100%%" style="max-width: 600px; margin: auto; background-color: white; border-radius: 8px; overflow: hidden;">
                    <tr style="background-color: #222;">
                      <td style="padding: 20px; text-align: center;">
                        <img src="https://labrujastore.com.pe/general/img/logo.png" alt="LaBrujaStore" style="width: 150px;">
                      </td>
                    </tr>
                    <tr>
                      <td style="padding: 30px;">
                        <h2 style="color: #4CAF50;">¡Tu pedido ha cambiado de estado!</h2>
                        <p>Hola <strong>%s</strong>,</p>
                        <p>Tu compra <strong>#%s</strong> ha sido actualizada.</p>
                        <p>
                          <strong>Estado anterior:</strong> %s<br>
                          <strong>Nuevo estado:</strong> <span style="color: #4CAF50;">%s</span>
                        </p>
                        <p>Haz clic en el siguiente botón para ver los detalles:</p>
                        <div style="text-align: center; margin: 20px 0;">
                          <a href="http://localhost:5173/%s"
                             style="background-color: #4CAF50; color: white; padding: 12px 24px; text-decoration: none; border-radius: 6px;">
                            Ver mi compra
                          </a>
                        </div>
                        <p>Gracias por confiar en <strong>LaBrujaStore</strong>.</p>
                        <p style="color: gray; font-size: 12px;">Este es un mensaje automático, no respondas este correo.</p>
                      </td>
                    </tr>
                  </table>
                </body>
                </html>
            """;
            // (Tu HTML completo va aquí)

            String htmlContent = String.format(htmlTemplate,
                    nombreCliente,
                    idCompra,
                    estadoAnterior,
                    nuevoEstado,
                    idCompra
            );

            helper.setText(htmlContent, true);
            javaMailSender.send(message);

        } catch (MessagingException e) {
            System.err.println("Error al enviar el correo HTML: " + e.getMessage());
        }
    }

}
