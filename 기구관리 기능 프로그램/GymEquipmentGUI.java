import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

/**
 * GymEquipmentGUI 클래스는 체육관 기구를 관리하기 위한 GUI 프로그램입니다.
 * 기구 추가, 삭제, 상태 조회 기능을 제공합니다.
 */
public class GymEquipmentGUI extends JFrame {
    private HashMap<String, Equipment> equipmentMap = new HashMap<>();
    private DefaultListModel<String> equipmentListModel = new DefaultListModel<>();

    /**
     * GymEquipmentGUI 생성자는 GUI를 초기화하고 이벤트를 설정합니다.
     */
    public GymEquipmentGUI() {
        // FlatLaf 테마 적용 (FlatLightLaf 사용)
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setTitle("체육관 기구 관리 시스템 🏋️‍♂️");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 상단 헤더 패널
        JLabel headerLabel = new JLabel("체육관 기구 관리 시스템", JLabel.CENTER);
        headerLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(72, 128, 255));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setPreferredSize(new Dimension(getWidth(), 50));
        add(headerLabel, BorderLayout.NORTH);

        // 메인 패널
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 기구 목록
        JList<String> equipmentListView = new JList<>(equipmentListModel);
        equipmentListView.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(equipmentListView);
        scrollPane.setBorder(BorderFactory.createTitledBorder("기구 목록"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton addButton = new JButton("➕ 기구 추가");
        JButton deleteButton = new JButton("❌ 기구 삭제");
        JButton viewStatusButton = new JButton("📋 상태 조회");

        // 버튼 스타일 적용
        for (JButton button : new JButton[]{addButton, deleteButton, viewStatusButton}) {
            button.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            button.setBackground(new Color(72, 128, 255));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        }

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewStatusButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        // 이벤트 처리
        addButton.addActionListener(e -> addEquipment());
        deleteButton.addActionListener(e -> deleteEquipment());
        viewStatusButton.addActionListener(e -> viewEquipmentStatus());
    }

    /**
     * addEquipment 메서드는 새로운 기구를 추가합니다.
     */
    private void addEquipment() {
        JTextField nameField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField statusField = new JTextField();

        Object[] message = {
                "기구 이름:", nameField,
                "수량:", quantityField,
                "상태:", statusField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "기구 추가", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                String status = statusField.getText();

                Equipment newEquipment = new Equipment(name, quantity, status);
                equipmentMap.put(name, newEquipment);
                updateEquipmentListModel();
                JOptionPane.showMessageDialog(this, "기구가 추가되었습니다.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "수량은 숫자로 입력해야 합니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

        
    /**
     * deleteEquipment 메서드는 특정 기구를 삭제합니다.
     */
    private void deleteEquipment() {
        String name = JOptionPane.showInputDialog(this, "삭제할 기구 이름을 입력하세요:");
        if (name != null && !name.isEmpty()) {
            if (equipmentMap.containsKey(name)) {
                equipmentMap.remove(name);
                updateEquipmentListModel();
                JOptionPane.showMessageDialog(this, "기구가 삭제되었습니다.");
            } else {
                JOptionPane.showMessageDialog(this, "해당 이름의 기구를 찾을 수 없습니다.");
            }
        }
    }

    /**
     * viewEquipmentStatus 메서드는 기구 상태를 조회합니다.
     */
    private void viewEquipmentStatus() {
        if (equipmentMap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "등록된 기구가 없습니다.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Equipment equipment : equipmentMap.values()) {
                sb.append(equipment.getName())
                        .append(" - 수량: ")
                        .append(equipment.getQuantity())
                        .append(", 상태: ")
                        .append(equipment.getStatus())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "기구 상태", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * updateEquipmentListModel 메서드는 JList 모델을 갱신합니다.
     */
    private void updateEquipmentListModel() {
        equipmentListModel.clear();
        for (Equipment equipment : equipmentMap.values()) {
            equipmentListModel.addElement(equipment.getName() + " (" + equipment.getQuantity() + "개, 상태: " + equipment.getStatus() + ")");
        }
    }

    /**
     * main 메서드는 프로그램을 실행합니다.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GymEquipmentGUI gui = new GymEquipmentGUI();
            gui.setVisible(true);
        });
    }
}

/**
 * Equipment 클래스는 체육관 기구의 정보를 저장합니다.
 */
class Equipment {
    private String name;
    private int quantity;
    private String status;

    public Equipment(String name, int quantity, String status) {
        this.name = name;
        this.quantity = quantity;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }
}
