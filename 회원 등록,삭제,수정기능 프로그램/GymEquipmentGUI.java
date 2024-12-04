import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * GymEquipmentGUI 클래스는 체육관 기구를 관리하기 위한 GUI 프로그램입니다.
 * 기구 추가, 삭제, 상태 조회 기능을 제공합니다.
 */
public class GymEquipmentGUI extends JFrame {
    // 기구 데이터를 저장하는 ArrayList
    private ArrayList<Equipment> equipmentList = new ArrayList<>();
    // 기구 목록을 표시하는 리스트 모델
    private DefaultListModel<String> equipmentListModel = new DefaultListModel<>();

    /**
     * GymEquipmentGUI 생성자는 GUI를 초기화하고 이벤트를 설정합니다.
     * 기구 관리 기능을 수행하기 위한 각 버튼과 화면 구성을 정의합니다.
     */
    public GymEquipmentGUI() {
        // GUI의 기본 설정
        setTitle("체육관 기구 관리 시스템");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 메인 패널 생성
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 기구 목록을 표시하는 리스트 구성
        JList<String> equipmentListView = new JList<>(equipmentListModel);
        JScrollPane scrollPane = new JScrollPane(equipmentListView);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 버튼 패널 생성 및 구성
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("기구 추가");
        JButton deleteButton = new JButton("기구 삭제");
        JButton viewStatusButton = new JButton("기구 상태 조회");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewStatusButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // 이벤트 처리
        addButton.addActionListener(e -> addEquipment());
        deleteButton.addActionListener(e -> deleteEquipment());
        viewStatusButton.addActionListener(e -> viewEquipmentStatus());
    }

    /**
     * addEquipment 메서드는 새로운 기구를 추가하기 위한 대화 상자를 표시하고,
     * 입력된 정보를 ArrayList에 추가합니다.
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

                equipmentList.add(new Equipment(name, quantity, status));
                equipmentListModel.addElement(name + " (" + quantity + "개, 상태: " + status + ")");
                JOptionPane.showMessageDialog(this, "기구가 추가되었습니다.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "수량은 숫자로 입력해야 합니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * deleteEquipment 메서드는 특정 기구를 삭제하기 위한 대화 상자를 표시하고,
     * 입력된 이름을 기준으로 ArrayList에서 해당 기구를 삭제합니다.
     */
    private void deleteEquipment() {
        String name = JOptionPane.showInputDialog(this, "삭제할 기구 이름을 입력하세요:");
        if (name != null && !name.isEmpty()) {
            boolean removed = equipmentList.removeIf(equipment -> equipment.getName().equals(name));
            if (removed) {
                equipmentListModel.clear();
                for (Equipment equipment : equipmentList) {
                    equipmentListModel.addElement(equipment.getName() + " (" + equipment.getQuantity() + "개, 상태: " + equipment.getStatus() + ")");
                }
                JOptionPane.showMessageDialog(this, "기구가 삭제되었습니다.");
            } else {
                JOptionPane.showMessageDialog(this, "해당 이름의 기구를 찾을 수 없습니다.");
            }
        }
    }

    /**
     * viewEquipmentStatus 메서드는 등록된 기구의 상태를 대화 상자로 표시합니다.
     */
    private void viewEquipmentStatus() {
        if (equipmentList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "등록된 기구가 없습니다.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Equipment equipment : equipmentList) {
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
     * main 메서드는 프로그램의 진입점으로, GymEquipmentGUI 인스턴스를 생성하고 실행합니다.
     * @param args 명령행 인수 (현재 사용되지 않음)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GymEquipmentGUI gui = new GymEquipmentGUI();
            gui.setVisible(true);
        });
    }
}

/**
 * Equipment 클래스는 체육관 기구의 정보를 저장하는 데이터 모델입니다.
 * 기구 이름, 수량, 상태를 포함합니다.
 */
class Equipment {
    private String name;
    private int quantity;
    private String status;

    /**
     * Equipment 생성자는 기구의 이름, 수량, 상태를 초기화합니다.
     * @param name 기구 이름
     * @param quantity 기구 수량
     * @param status 기구 상태
     */
    public Equipment(String name, int quantity, String status) {
        this.name = name;
        this.quantity = quantity;
        this.status = status;
    }

    /**
     * getName 메서드는 기구의 이름을 반환합니다.
     * @return 기구 이름
     */
    public String getName() {
        return name;
    }

    /**
     * getQuantity 메서드는 기구의 수량을 반환합니다.
     * @return 기구 수량
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * getStatus 메서드는 기구의 상태를 반환합니다.
     * @return 기구 상태
     */
    public String getStatus() {
        return status;
    }
}
