package fi.muni.pv168.ui.form;

import fi.muni.pv168.Knight;
import fi.muni.pv168.ui.resources.Resources;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;

public class KnightForm {

    private Knight editable;

    private FormResultListener<Knight> listener;

    private JTextField name;
    private JTextField castle;
    private JTextField heraldry;
    private JDatePickerImpl born;

    private JFrame frame;

    public KnightForm(FormResultListener<Knight> listener) {
        this(null, listener);
    }

    public KnightForm(Knight editable, FormResultListener<Knight> listener) {
        this.editable = editable;
        this.listener = listener;

        EventQueue.invokeLater(new Runnable() {
            public void run() {

                frame = new JFrame();
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setMinimumSize(new Dimension(320, 235));
                frame.setBounds(0, 0, 320, 235);
                frame.setResizable(false);
                frame.setTitle(Resources.getString("knight_editor"));

                frame.add(initCreateForm());
                // Zobrazíme okno
                frame.setVisible(true);
            }
        });

    }

    private JPanel initCreateForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        name = new JTextField();
        castle = new JTextField();
        heraldry = new JTextField();

        Date date = new Date(Calendar.getInstance().getTime().getTime());
        if (editable != null) {
            name.setText(editable.getName());
            castle.setText(editable.getCastle());
            heraldry.setText(editable.getHeraldry());
            date = editable.getBorn();
        }
        born = new JDatePickerImpl(new JDatePanelImpl(new SqlDateModel(date)));

        JLabel title = new JLabel(Resources.getString("knight_editor"));
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, (int) (title.getFont().getSize() * 1.5)));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        constraints.insets = new Insets(10,10,10,10);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(title, constraints);

        JLabel nameLabel = new JLabel(Resources.getString("name"));
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel.setLabelFor(name);
        JLabel castleLabel = new JLabel(Resources.getString("castle"));
        castleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        castleLabel.setLabelFor(castle);
        JLabel bornLabel = new JLabel(Resources.getString("born"));
        bornLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        bornLabel.setLabelFor(born);
        JLabel heraldryLabel = new JLabel(Resources.getString("heraldry"));
        heraldryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        heraldryLabel.setLabelFor(heraldry);

        JComponent[] objects = new JComponent[ ] {
                nameLabel, name, castleLabel, castle, bornLabel, born, heraldryLabel, heraldry
        };

        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0.2;
        constraints.insets = new Insets(5, 5, 5, 5);
        for (JComponent c : objects) {
            panel.add(c, constraints);
            constraints.gridx++;
            constraints.weightx = 0.7;
            constraints.insets = new Insets(0,0,0,30);
            if (constraints.gridx == 2) {
                constraints.gridx = 0;
                constraints.gridy++;
                constraints.insets = new Insets(5,5,5,5);
                constraints.weightx = 0.2;
            }
        }

        constraints.gridy++;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(10,10,10,10);
        constraints.weighty = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        JButton button = new JButton(Resources.getString("cancel"));
        button.addActionListener(cancel);
        panel.add(button, constraints);
        constraints.gridx = 1;
        button = new JButton(Resources.getString("save"));
        button.addActionListener(submit);
        panel.add(button, constraints);

        frame.getRootPane().setDefaultButton(button);

        panel.setPreferredSize(new Dimension(320, 100));

        return panel;
    }

    ActionListener cancel = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (listener != null) {
                listener.onCancel();
            }
            frame.dispose();
        }
    };

    ActionListener submit = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            //validate form
            if (name.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, Resources.getString("name_too_short"));
                return;
            }
            if (castle.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, Resources.getString("castle_too_short"));
                return;
            }
            if (heraldry.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, Resources.getString("heraldry_too_short"));
                return;
            }
            if (editable != null) {
                editable.setName(name.getText());
                editable.setCastle(castle.getText());
                editable.setBorn((Date) born.getModel().getValue());
                editable.setHeraldry(heraldry.getText());
            } else {
                editable = new Knight(null, name.getText(), castle.getText(), (Date) born.getModel().getValue(), heraldry.getText());
            }
            if (listener != null) {
                listener.onSubmit(editable);
            }
            frame.dispose();
        }
    };
}
