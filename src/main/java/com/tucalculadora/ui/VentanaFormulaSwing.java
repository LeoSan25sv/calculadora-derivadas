package com.tucalculadora.ui;

import com.tucalculadora.util.LaTeXRendererSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class VentanaFormulaSwing extends JFrame {
    private JLabel etiquetaFormula;

    public VentanaFormulaSwing() {
        setTitle("Calculadora de Derivadas - LaTeX");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 300);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Etiqueta para mostrar la fórmula
        etiquetaFormula = new JLabel();
        etiquetaFormula.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaFormula.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(etiquetaFormula, BorderLayout.CENTER);

        add(panel);
    }

    /**
     * Muestra una fórmula en la ventana.
     *
     * @param formula Código LaTeX de la fórmula
     */
    public void mostrarFormula(String formula) {
        BufferedImage imagen = LaTeXRendererSwing.renderizar(formula, 24);
        if (imagen != null) {
            etiquetaFormula.setIcon(new ImageIcon(imagen));
        } else {
            etiquetaFormula.setText("Error al renderizar: " + formula);
        }
    }

    /**
     * Muestra dos fórmulas en la ventana (f(x) y f'(x)).
     */
    public void mostrarFormulas(String f, String fPrima) {
        // Crear un panel con dos imágenes
        BufferedImage imgF = LaTeXRendererSwing.renderizar("f(x) = " + f, 24);
        BufferedImage imgFPrima = LaTeXRendererSwing.renderizar("f'(x) = " + fPrima, 24);

        if (imgF != null && imgFPrima != null) {
            // Crear una imagen combinada
            int width = Math.max(imgF.getWidth(), imgFPrima.getWidth()) + 40;
            int height = imgF.getHeight() + imgFPrima.getHeight() + 40;

            BufferedImage combinada = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = combinada.createGraphics();
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, width, height);

            // Dibujar f(x)
            int xPos = (width - imgF.getWidth()) / 2;
            g2.drawImage(imgF, xPos, 10, null);

            // Dibujar f'(x)
            xPos = (width - imgFPrima.getWidth()) / 2;
            g2.drawImage(imgFPrima, xPos, imgF.getHeight() + 20, null);

            g2.dispose();
            etiquetaFormula.setIcon(new ImageIcon(combinada));
        } else {
            etiquetaFormula.setText("Error al renderizar fórmulas");
        }
    }
}