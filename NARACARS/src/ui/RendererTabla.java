package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class RendererTabla extends DefaultTableCellRenderer {

	public RendererTabla() {
		super();
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		JLabel label = new JLabel((String) value);
		label.setForeground(Color.red);
		label.setFont(new Font("Arial", Font.BOLD,40 ));
		table.setRowHeight(100);
		return label;
	}
	
	

}
