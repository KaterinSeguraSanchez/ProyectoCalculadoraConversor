package calcular;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calcular extends JFrame implements ActionListener {

    private static final double TASA_DOLAR = 3800.0;

    private JTextField txtNum1, txtNum2, txtResultado;

    private JButton btnSumar, btnRestar, btnMultiplicar, btnDividir;
    private JButton btnCtoF, btnFtoC, btnUsdToCop, btnCopToUsd;

    public Calcular() {

        setTitle("Calculadora y Conversor");
        setSize(550, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNum1 = new JTextField(15);
        txtNum2 = new JTextField(15);
        txtResultado = new JTextField(15);

        txtResultado.setEditable(false);
        txtResultado.setForeground(Color.BLUE);
        txtResultado.setFont(new Font("Arial", Font.BOLD, 14));

        btnSumar = crearBoton("SUMAR", "SUMAR");
        btnRestar = crearBoton("RESTAR", "RESTAR");
        btnMultiplicar = crearBoton("MULTIPLICAR", "MULTIPLICAR");
        btnDividir = crearBoton("DIVIDIR", "DIVIDIR");

        btnCtoF = crearBoton("°C → °F", "CTOF");
        btnFtoC = crearBoton("°F → °C", "FTOC");

        btnUsdToCop = crearBoton("USD → COP", "USDCOP");
        btnCopToUsd = crearBoton("COP → USD", "COPUSD");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Valor 1 / Cantidad:"), gbc);

        gbc.gridx = 1;
        add(txtNum1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Valor 2:"), gbc);

        gbc.gridx = 1;
        add(txtNum2, gbc);

        JPanel panelMat = new JPanel(new GridLayout(1, 4, 5, 5));
        panelMat.setBorder(BorderFactory.createTitledBorder("Operaciones"));
        panelMat.add(btnSumar);
        panelMat.add(btnRestar);
        panelMat.add(btnMultiplicar);
        panelMat.add(btnDividir);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(panelMat, gbc);

        JPanel panelTemp = new JPanel(new GridLayout(1, 2, 5, 5));
        panelTemp.setBorder(BorderFactory.createTitledBorder("Temperatura"));
        panelTemp.add(btnCtoF);
        panelTemp.add(btnFtoC);

        gbc.gridy = 3;
        add(panelTemp, gbc);

        JPanel panelMoneda = new JPanel(new GridLayout(1, 2, 5, 5));
        panelMoneda.setBorder(
                BorderFactory.createTitledBorder("Moneda (1 USD = $3800)")
        );

        panelMoneda.add(btnUsdToCop);
        panelMoneda.add(btnCopToUsd);

        gbc.gridy = 4;
        add(panelMoneda, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("RESULTADO:"), gbc);

        gbc.gridx = 1;
        add(txtResultado, gbc);
    }

    private JButton crearBoton(String texto, String comando) {
        JButton boton = new JButton(texto);
        boton.setActionCommand(comando);
        boton.addActionListener(this);
        return boton;
    }

    private Double obtenerNumero(JTextField campo, String nombre) {
        try {
            return Double.parseDouble(campo.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese un número válido en " + nombre,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return null;
        }
    }

    private void mostrarResultado(String resultado) {
        txtResultado.setText(resultado);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Double valor1 = obtenerNumero(txtNum1, "Valor 1");

        if (valor1 == null) return;

        String accion = e.getActionCommand();

        if (accion.equals("SUMAR") || accion.equals("RESTAR")
                || accion.equals("MULTIPLICAR") || accion.equals("DIVIDIR")) {

            Double valor2 = obtenerNumero(txtNum2, "Valor 2");

            if (valor2 == null) return;

            double resultado = 0;

            switch (accion) {

                case "SUMAR":
                    resultado = valor1 + valor2;
                    break;

                case "RESTAR":
                    resultado = valor1 - valor2;
                    break;

                case "MULTIPLICAR":
                    resultado = valor1 * valor2;
                    break;

                case "DIVIDIR":
                    if (valor2 == 0) {
                        JOptionPane.showMessageDialog(
                                this,
                                "No se puede dividir entre cero",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    resultado = valor1 / valor2;
                    break;
            }

            mostrarResultado(String.format("%.2f", resultado));
            return;
        }

        switch (accion) {

            case "CTOF":
                mostrarResultado(
                        String.format("%.2f °F", (valor1 * 9 / 5) + 32)
                );
                break;

            case "FTOC":
                mostrarResultado(
                        String.format("%.2f °C", (valor1 - 32) * 5 / 9)
                );
                break;

            case "USDCOP":
                mostrarResultado(
                        String.format("$%.2f COP", valor1 * TASA_DOLAR)
                );
                break;

            case "COPUSD":
                mostrarResultado(
                        String.format("$%.2f USD", valor1 / TASA_DOLAR)
                );
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new Calcular().setVisible(true)
        );
    }
}
