import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * GymMembershipGUI 클래스는 체육관 회원을 관리하기 위한 GUI 프로그램입니다.
 * 회원 추가, 삭제, 조회 기능을 제공하며, ArrayList를 사용해 회원 데이터를 관리합니다.
 */
public class GymMembershipGUI extends JFrame {
    // 회원 데이터를 저장하는 ArrayList
    private ArrayList<Member> members = new ArrayList<>();
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
        // 회원 추가를 위한 입력 창 구성
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField membershipField = new JTextField();

        Object[] message = {
                "이름:", nameField,
                "나이:", ageField,
                "회원권 종류:", membershipField
        };

        // 입력 받은 정보를 처리
        int option = JOptionPane.showConfirmDialog(this, message, "회원 추가", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String membershipType = membershipField.getText();

                // 새로운 회원을 리스트에 추가
                members.add(new Member(name, age, membershipType));
                memberListModel.addElement(name + " (" + membershipType + ")");
                JOptionPane.showMessageDialog(this, "회원이 추가되었습니다.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "나이는 숫자로 입력해야 합니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * deleteMember 메서드는 회원 삭제를 위한 대화 상자를 열고,
     * 입력된 이름을 기준으로 회원 리스트에서 해당 회원을 삭제합니다.
     */
    private void deleteMember() {
        // 삭제할 회원 이름 입력 창
        String name = JOptionPane.showInputDialog(this, "삭제할 회원의 이름을 입력하세요:");
        if (name != null && !name.isEmpty()) {
            boolean removed = members.removeIf(member -> member.getName().equals(name));
            if (removed) {
                // 목록에서 해당 회원 제거
                memberListModel.clear();
                for (Member member : members) {
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
            for (Member member : members) {
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
 * 이름, 나이, 회원권 종류를 포함합니다.
 */
class Member {
    private String name;
    private int age;
    private String membershipType;

    /**
     * Member 생성자는 회원의 이름, 나이, 회원권 종류를 초기화합니다.
     * @param name 회원 이름
     * @param age 회원 나이
     * @param membershipType 회원권 종류
     */
    public Member(String name, int age, String membershipType) {
        this.name = name;
        this.age = age;
        this.membershipType = membershipType;
    }

    /**
     * getName 메서드는 회원의 이름을 반환합니다.
     * @return 회원 이름
     */
    public String getName() {
        return name;
    }

    /**
     * getAge 메서드는 회원의 나이를 반환합니다.
     * @return 회원 나이
     */
    public int getAge() {
        return age;
    }

    /**
     * getMembershipType 메서드는 회원의 회원권 종류를 반환합니다.
     * @return 회원권 종류
     */
    public String getMembershipType() {
        return membershipType;
    }
}
