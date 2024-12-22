import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class GymManagementSystemGUI extends JFrame {

    private JTabbedPane tabbedPane; // 탭을 관리하는 컴포넌트

    public GymManagementSystemGUI() {
        setTitle("체육관 관리 시스템");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        // 각 탭에 대한 GUI를 설정
        tabbedPane.addTab("공지사항 관리", new NoticeManagementPanel());
        tabbedPane.addTab("회원 관리", new GymMembershipPanel());
        tabbedPane.addTab("기구 관리", new GymEquipmentPanel());

        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GymManagementSystemGUI gui = new GymManagementSystemGUI();
            gui.setVisible(true);
        });
    }

    // 공지사항 관리 패널
    class NoticeManagementPanel extends JPanel {
        private JTextArea textArea;
        private JTextField textFieldTitle;
        private Map<Integer, Notice> noticeMap;
        private int currentId;

        public NoticeManagementPanel() {
            setLayout(new BorderLayout());

            noticeMap = new HashMap<>();
            currentId = 1;

            JPanel panel = new JPanel();
            add(panel, BorderLayout.NORTH);

            panel.add(new JLabel("공지사항 제목:"));
            textFieldTitle = new JTextField(20);
            panel.add(textFieldTitle);

            JButton btnAdd = new JButton("공지사항 추가");
            btnAdd.addActionListener(e -> addNotice());
            panel.add(btnAdd);

            JButton btnEdit = new JButton("공지사항 수정");
            btnEdit.addActionListener(e -> editNotice());
            panel.add(btnEdit);

            JButton btnDelete = new JButton("공지사항 삭제");
            btnDelete.addActionListener(e -> deleteNotice());
            panel.add(btnDelete);

            textArea = new JTextArea();
            add(new JScrollPane(textArea), BorderLayout.CENTER);

            loadNotices();
        }

        private void addNotice() {
            String title = textFieldTitle.getText();
            if (!title.isEmpty()) {
                Notice notice = new Notice(currentId, title);
                noticeMap.put(currentId, notice);
                currentId++;
                loadNotices();
            } else {
                JOptionPane.showMessageDialog(this, "제목을 입력하세요.");
            }
        }

        private void editNotice() {
            String title = textFieldTitle.getText();
            String inputId = JOptionPane.showInputDialog(this, "수정할 공지사항 ID를 입력하세요:");
            try {
                int id = Integer.parseInt(inputId);
                if (noticeMap.containsKey(id)) {
                    Notice notice = noticeMap.get(id);
                    notice.setTitle(title);
                    loadNotices();
                } else {
                    JOptionPane.showMessageDialog(this, "해당 ID의 공지사항이 없습니다.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "유효한 숫자를 입력하세요.");
            }
        }

        private void deleteNotice() {
            String inputId = JOptionPane.showInputDialog(this, "삭제할 공지사항 ID를 입력하세요:");
            try {
                int id = Integer.parseInt(inputId);
                if (noticeMap.containsKey(id)) {
                    noticeMap.remove(id);
                    loadNotices();
                } else {
                    JOptionPane.showMessageDialog(this, "해당 ID의 공지사항이 없습니다.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "유효한 숫자를 입력하세요.");
            }
        }

        private void loadNotices() {
            textArea.setText("");
            for (Notice notice : noticeMap.values()) {
                textArea.append("ID: " + notice.getId() + " | 제목: " + notice.getTitle() + "\n");
            }
        }
    }

    // 회원 관리 패널
    class GymMembershipPanel extends JPanel {
        private GymMembershipManager manager;
        private DefaultListModel<String> memberListModel;

        public GymMembershipPanel() {
            manager = new GymMembershipManager();
            memberListModel = new DefaultListModel<>();

            setLayout(new BorderLayout());

            JList<String> memberList = new JList<>(memberListModel);
            JScrollPane scrollPane = new JScrollPane(memberList);
            add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton addButton = new JButton("회원 추가");
            JButton deleteButton = new JButton("회원 삭제");
            JButton listButton = new JButton("회원 목록");

            buttonPanel.add(addButton);
            buttonPanel.add(deleteButton);
            buttonPanel.add(listButton);

            add(buttonPanel, BorderLayout.SOUTH);

            addButton.addActionListener(e -> addMember());
            deleteButton.addActionListener(e -> deleteMember());
            listButton.addActionListener(e -> listMembers());
        }

        private void addMember() {
            JTextField nameField = new JTextField();
            JTextField ageField = new JTextField();
            JTextField membershipField = new JTextField();

            Object[] message = {
                "이름:", nameField,
                "나이:", ageField,
                "회원권 종류:", membershipField
            };

            int option = JOptionPane.showConfirmDialog(this, message, "회원 추가", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String membershipType = membershipField.getText();

                    if (manager.addMember(name, age, membershipType)) {
                        updateMemberList();
                        JOptionPane.showMessageDialog(this, "회원이 추가되었습니다.");
                    } else {
                        JOptionPane.showMessageDialog(this, "이미 등록된 이름입니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "나이는 숫자로 입력해야 합니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private void deleteMember() {
            String name = JOptionPane.showInputDialog(this, "삭제할 회원의 이름을 입력하세요:");
            if (name != null && !name.isEmpty()) {
                if (manager.deleteMember(name)) {
                    updateMemberList();
                    JOptionPane.showMessageDialog(this, "회원이 삭제되었습니다.");
                } else {
                    JOptionPane.showMessageDialog(this, "해당 이름의 회원을 찾을 수 없습니다.");
                }
            }
        }

        private void listMembers() {
            String members = manager.listMembers();
            JOptionPane.showMessageDialog(this, members, "회원 목록", JOptionPane.INFORMATION_MESSAGE);
        }

        private void updateMemberList() {
            memberListModel.clear();
            for (Member member : manager.getAllMembers().values()) {
                memberListModel.addElement(member.getName() + " (" + member.getMembershipType() + ")");
            }
        }
    }

    // 기구 관리 패널
    class GymEquipmentPanel extends JPanel {
        private HashMap<String, Equipment> equipmentMap = new HashMap<>();
        private DefaultListModel<String> equipmentListModel = new DefaultListModel<>();

        public GymEquipmentPanel() {
            setLayout(new BorderLayout());

            JList<String> equipmentListView = new JList<>(equipmentListModel);
            JScrollPane scrollPane = new JScrollPane(equipmentListView);
            add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
            JButton addButton = new JButton("➕ 기구 추가");
            JButton deleteButton = new JButton("❌ 기구 삭제");
            JButton viewStatusButton = new JButton("📋 상태 조회");

            buttonPanel.add(addButton);
            buttonPanel.add(deleteButton);
            buttonPanel.add(viewStatusButton);

            add(buttonPanel, BorderLayout.SOUTH);

            addButton.addActionListener(e -> addEquipment());
            deleteButton.addActionListener(e -> deleteEquipment());
            viewStatusButton.addActionListener(e -> viewEquipmentStatus());
        }

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

        private void updateEquipmentListModel() {
            equipmentListModel.clear();
            for (Equipment equipment : equipmentMap.values()) {
                equipmentListModel.addElement(equipment.getName() + " (" + equipment.getQuantity() + "개, 상태: " + equipment.getStatus() + ")");
            }
        }
    }

    // 공지사항 데이터 모델
    class Notice {
        private int id;
        private String title;

        public Notice(int id, String title) {
            this.id = id;
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    // 체육관 회원 데이터 모델
    class Member {
        private String name;
        private int age;
        private String membershipType;

        public Member(String name, int age, String membershipType) {
            this.name = name;
            this.age = age;
            this.membershipType = membershipType;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getMembershipType() {
            return membershipType;
        }
    }

    // 기구 데이터 모델
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

    // 체육관 회원 관리
    class GymMembershipManager {
        private HashMap<String, Member> members = new HashMap<>();

        public boolean addMember(String name, int age, String membershipType) {
            if (members.containsKey(name)) {
                return false;
            }
            members.put(name, new Member(name, age, membershipType));
            return true;
        }

        public boolean deleteMember(String name) {
            return members.remove(name) != null;
        }

        public String listMembers() {
            if (members.isEmpty()) {
                return "등록된 회원이 없습니다.";
            }

            StringBuilder sb = new StringBuilder();
            for (Member member : members.values()) {
                sb.append(member.getName())
                        .append(" - 나이: ")
                        .append(member.getAge())
                        .append(", 회원권: ")
                        .append(member.getMembershipType())
                        .append("\n");
            }
            return sb.toString();
        }

        public HashMap<String, Member> getAllMembers() {
            return members;
        }
    }
}
