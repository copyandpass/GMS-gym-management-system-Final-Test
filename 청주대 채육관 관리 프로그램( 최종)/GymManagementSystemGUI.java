import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

/**
 ** 2021011932 이진혁 기말고사 프로그램입니다.
 * 청주대학교  체육관 관리 시스템의 GUI를 구현한 클래스입니다.
 * 이 프로그램은 체육관의 공지사항, 회원, 기구를 관리하는 기능을 제공합니다.
 * <p>문서 작성이니 최대한 두서없이 기능들에 대한 설명만 넣어 놓은점 양해 부탁드리겠습니다.
 * 이번 자바 프로그래밍을 들으면서 저의 진로에 조금더 애정이 붙었습니다. 감사합니다.
 *  이번학기 고생많으셨습니다.
 * 
 * <p> 제가 준비한 프로그램은 3개의 주요 탭을 가지고 있습니다:
 * <ul>
 * <li>공지사항 관리: 공지사항을 추가, 수정, 삭제할 수 있습니다.</li>
 * <li>회원 관리: 회원을 추가, 삭제하고 목록을 조회할 수 있습니다.</li>
 * <li>기구 관리: 기구를 추가, 삭제하고 상태를 조회할 수 있습니다.</li>
 * <li>--------------------------------------------------------------------------
 * <li>시작하기에 앞서 컬렉션 프레임워크 기능이 들어간 메소드들을 먼저 설명하고 가겠습니다.
 * <li>공지사항 관리: HashMap으로 ID를 기준으로 효율적으로 공지사항을 관리.
 * <li>회원 관리: HashMap을 사용해 회원 이름을 키로 하여 데이터를 효율적으로 추가, 삭제, 검색.
 * <li>기구 관리: HashMap으로 기구 이름과 관련 데이터를 효율적으로 관리.
 * <li>UI 데이터 표시: DefaultListModel을 사용해 회원 및 기구 데이터를 리스트 형태로 UI에 연동.
 * </ul>
 */
public class GymManagementSystemGUI extends JFrame {

    private JTabbedPane tabbedPane; // 탭을 관리하는 컴포넌트

    
    
    
    /**
     * 먼저 GymManagementSystemGUI 클래스의 생성자입니다.
     * 이 생성자로 기본 GUI를 설정하고, 각 탭을 초기화하여 프레임에 추가했습니다.
     */
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

    /**
     * 다음으로는 메인 메서드입니다.
     * 이 메서드는 프로그램을 실행시키는 역할을 합니다.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GymManagementSystemGUI gui = new GymManagementSystemGUI();
            gui.setVisible(true);
        });
    }

    // 공지사항 관리 패널
    /**
     * 2번째 기능인 공지사항을 추가, 수정, 삭제하는 패널입니다.
     **공지사항을 리스트 형태로 관리하고, 프로그램 사용자가 공지사항을 추가하거나 수정, 삭제할 수 있습니다.
     *
     * <li>앞으로 청주대학교 관리자가 사용할 기능이라 권한부여가필요합니다. (구현못한기능)
     
     */
    class NoticeManagementPanel extends JPanel {
        private JTextArea textArea;
        private JTextField textFieldTitle;
        private Map<Integer, Notice> noticeMap;
        private int currentId;

        /**
         * NoticeManagementPanel의 생성자입니다.
         * 이 생성자는 체육관 프로그램에서 공지사항 관리 기능을 위한 UI 요소들을 설정합니다.
         *  <li> ex) 추가, 삭제, 수정
         */
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

        /**
         * 새로운 공지사항을 추가하는 메서드입니다.
         * 사용자가 입력한 제목을 기반으로 새로운 공지사항을 추가합니다.
         */
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

        /**
         * 공지사항을 수정하는 메서드입니다.
         * 사용자가 입력한 ID를 바탕으로 수정할 공지사항을 찾아 제목을 수정합니다.
         * <li>아직 권한부여 기능이 없기에 ID로 권한을 대신했습니다.
         */
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

        /**
         * 공지사항을 삭제하는 메서드입니다.
         * 사용자가 입력한 ID(권한)를 바탕으로 공지사항을 삭제합니다.
         */
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

        /**
         * 공지사항 목록을 화면에 가져오는 메서드입니다.
         * 현재 저장된 모든 공지사항을 텍스트 영역에 표시합니다.
         */
        private void loadNotices() {
            textArea.setText("");
            for (Notice notice : noticeMap.values()) {
                textArea.append("ID: " + notice.getId() + " | 제목: " + notice.getTitle() + "\n");
            }
        }
    }

    // 회원 관리 패널
    /**
     * 체육관 회원을 추가, 삭제 및 목록을 조회하는 패널입니다.
     * 회원의 이름, 나이, 회원권 종류를 관리합니다.
     */
    class GymMembershipPanel extends JPanel {
        private GymMembershipManager manager;
        private DefaultListModel<String> memberListModel;

        /**
         * GymMembershipPanel의 생성자입니다.
         * 이 생성자는 회원 관리 기능을 위한 UI 요소들을 설정합니다.
         */
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

        /**
         * 새로운 회원을 추가하는 메서드입니다.
         * 사용자가 입력한 정보를 바탕으로 새로운 회원을 추가합니다.
         */
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

        /**
         * 회원을 삭제하는 메서드입니다.
         * 사용자가 입력한 이름을 바탕으로 회원을 삭제합니다.
         * 컬렉션 프레임워크 기능이 들어가있습니다.
         */
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

        /**
         * 등록된 모든 회원을 목록으로 조회하는 메서드입니다.
         */
        private void listMembers() {
            String members = manager.listMembers();
            JOptionPane.showMessageDialog(this, members, "회원 목록", JOptionPane.INFORMATION_MESSAGE);
        }

        /**
         * 회원 목록을 화면에 업데이트하는 메서드입니다.
         * 현재 저장된 모든 회원을 목록에 표시합니다.
         */
        private void updateMemberList() {
            memberListModel.clear();
            for (Member member : manager.getAllMembers().values()) {
                memberListModel.addElement(member.getName() + " (" + member.getMembershipType() + ")");
            }
        }
    }

    // 기구 관리 패널
    /**
     * 체육관의 기구를 추가, 삭제 및 상태를 조회하는 패널입니다.
     * 기구의 이름, 수량, 상태를 관리합니다.
     */
    class GymEquipmentPanel extends JPanel {
        private HashMap<String, Equipment> equipmentMap = new HashMap<>();
        private DefaultListModel<String> equipmentListModel = new DefaultListModel<>();

        /**
         * GymEquipmentPanel의 생성자입니다.
         * 이 생성자는 기구 관리 기능을 위한 UI 요소들을 설정합니다.
         */
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

        /**
         * 새로운 기구를 추가하는 메서드입니다.
         * 사용자가 입력한 정보를 바탕으로 기구를 추가합니다.
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
         * 기구를 삭제하는 메서드입니다.
         * 사용자가 입력한 이름을 바탕으로 기구를 삭제합니다.
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
         * 기구의 상태를 조회하는 메서드입니다.
         * 등록된 모든 기구의 상태와 수량을 보여줍니다.
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
         * 기구 목록을 화면에 업데이트하는 메서드입니다.
         * 현재 저장된 모든 기구를 목록에 표시합니다.
         */
        private void updateEquipmentListModel() {
            equipmentListModel.clear();
            for (Equipment equipment : equipmentMap.values()) {
                equipmentListModel.addElement(equipment.getName() + " (" + equipment.getQuantity() + "개, 상태: " + equipment.getStatus() + ")");
            }
        }
    }

    // 공지사항 데이터 모델
    /**
     * 공지사항을 나타내는 데이터 모델입니다.
     * 각 공지사항은 ID와 제목을 가지고 있습니다.
     */
    class Notice {
        private int id;
        private String title;

        /**
         * Notice 클래스의 생성자입니다.
         * @param id 공지사항의 ID
         * @param title 공지사항의 제목
         */
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
    /**
     * 체육관 회원을 나타내는 데이터 모델입니다.
     * 회원은 이름, 나이, 회원권 종류를 가집니다.
     */
    class Member {
        private String name;
        private int age;
        private String membershipType;

        /**
         * Member 클래스의 생성자입니다.
         * @param name 회원의 이름
         * @param age 회원의 나이
         * @param membershipType 회원의 회원권 종류
         */
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
    /**
     * 체육관 기구를 나타내는 데이터 모델입니다.
     * 기구는 이름, 수량, 상태를 가집니다.
     */
    class Equipment {
        private String name;
        private int quantity;
        private String status;

        /**
         * Equipment 클래스의 생성자입니다.
         * @param name 기구의 이름
         * @param quantity 기구의 수량
         * @param status 기구의 상태
         */
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
    /**
     * 체육관의 회원을 관리하는 클래스입니다.
     * 회원을 추가, 삭제하고, 목록을 조회할 수 있습니다.
     */
    class GymMembershipManager {
        private HashMap<String, Member> members = new HashMap<>();

        /**
         * 새로운 회원을 추가하는 메서드입니다.
         * @param name 회원의 이름
         * @param age 회원의 나이
         * @param membershipType 회원의 회원권 종류
         * @return 이미 등록된 회원이 아닌 경우 true, 그렇지 않으면 false
         */
        public boolean addMember(String name, int age, String membershipType) {
            if (members.containsKey(name)) {
                return false;
            }
            members.put(name, new Member(name, age, membershipType));
            return true;
        }

        /**
         * 회원을 삭제하는 메서드입니다.
         * @param name 삭제할 회원의 이름
         * @return 삭제된 회원이 있으면 true, 없으면 false
         */
        public boolean deleteMember(String name) {
            return members.remove(name) != null;
        }

        /**
         * 모든 회원의 목록을 조회하는 메서드입니다.
         * @return 등록된 회원들의 목록을 문자열로 반환
         */
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

        /**
         * 모든 회원을 반환하는 메서드입니다.
         * @return 회원 목록을 반환
         */
        public HashMap<String, Member> getAllMembers() {
            return members;
        }
    }
}


//마무리-- 2021011932 이진혁 자바프로그래밍 기말고사 프로그래밍 마침니다! 감사합니다.