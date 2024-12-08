import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

/**
 * GymMembershipGUI 클래스는 체육관 회원을 관리하기 위한 GUI 프로그램입니다.
 * 회원 추가, 삭제, 조회 기능을 제공하며, HashMap을 사용해 회원 데이터를 관리합니다.
 */
public class GymMembershipGUI extends JFrame {
    // 회원 데이터를 저장하는 HashMap
    private HashMap<String, Member> members = new HashMap<>();
    // 회원 목록을 표시하는 리스트 모델
    private DefaultListModel<String> memberListModel = new DefaultListModel<>();

    /**
     * GymMembershipGUI 생성자는 GUI 창을 초기화하고 이벤트를 설정합니다.
     * 회원 관리 기능을 실행하기 위한 각 버튼과 화면 구성을 정의합니다.
     */
    public GymMembershipGUI() {
        // GUI의 기본 설정
        setTitle("체육관 회원 관리 시스템");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // GUI의 메인 패널 생성
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // 회원 목록을 표시하는 리스트 구성
        JList<String> memberList = new JList<>(memberListModel);
        JScrollPane scrollPane = new JScrollPane(memberList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 버튼 패널 생성 및 구성
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("회원 추가");
        JButton deleteButton = new JButton("회원 삭제");
        JButton listButton = new JButton("회원 목록");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(listButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // 회원 추가 버튼 클릭 이벤트 처리
        addButton.addActionListener(e -> addMember());

        // 회원 삭제 버튼 클릭 이벤트 처리
        deleteButton.addActionListener(e -> deleteMember());

        // 회원 목록 버튼 클릭 이벤트 처리
        listButton.addActionListener(e -> listMembers());
    }

    /**
     * addMember 메서드는 회원 추가를 위한 대화 상자를 열고, 입력된 정보를 통해 새 회원을 등록합니다.
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

                // 이름 중복 체크
                if (members.containsKey(name)) {
                    JOptionPane.showMessageDialog(this, "이미 등록된 이름입니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // HashMap에 새로운 회원 추가
                Member newMember = new Member(name, age, membershipType);
                members.put(name, newMember);

                // 회원 목록에 추가
                memberListModel.addElement(name + " (" + membershipType + ")");
                JOptionPane.showMessageDialog(this, "회원이 추가되었습니다.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "나이는 숫자로 입력해야 합니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * deleteMember 메서드는 회원 삭제를 위한 대화 상자를 열고,
     * 입력된 이름을 기준으로 HashMap에서 해당 회원을 삭제합니다.
     */
    private void deleteMember() {
        String name = JOptionPane.showInputDialog(this, "삭제할 회원의 이름을 입력하세요:");
        if (name != null && !name.isEmpty()) {
            // HashMap에서 회원 삭제
            if (members.remove(name) != null) {
                // 회원 목록에서 제거
                memberListModel.clear();
                for (String key : members.keySet()) {
                    Member member = members.get(key);
                    memberListModel.addElement(member.getName() + " (" + member.getMembershipType() + ")");
                }
                JOptionPane.showMessageDialog(this, "회원이 삭제되었습니다.");
            } else {
                JOptionPane.showMessageDialog(this, "해당 이름의 회원을 찾을 수 없습니다.");
            }
        }
    }

    /**
     * listMembers 메서드는 현재 등록된 모든 회원의 정보를 대화 상자로 표시합니다.
     */
    private void listMembers() {
        if (members.isEmpty()) {
            JOptionPane.showMessageDialog(this, "등록된 회원이 없습니다.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Member member : members.values()) {
                sb.append(member.getName())
                        .append(" - 나이: ")
                        .append(member.getAge())
                        .append(", 회원권: ")
                        .append(member.getMembershipType())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "회원 목록", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * main 메서드는 프로그램의 진입점으로, GymMembershipGUI 인스턴스를 생성하고 실행합니다.
     * @param args 명령행 인수 (현재 사용되지 않음)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GymMembershipGUI gui = new GymMembershipGUI();
            gui.setVisible(true);
        });
    }
}

/**
 * Member 클래스는 체육관 회원의 정보를 저장하는 데이터 모델입니다.
 */
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
