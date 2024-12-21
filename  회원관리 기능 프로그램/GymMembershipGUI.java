import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

/**
 * GymMembershipManager 클래스는 회원 데이터를 관리하는 비즈니스 로직을 담당합니다.
 */
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

/**
 * GymMembershipGUI 클래스는 회원 관리 GUI를 담당합니다.
 */
public class GymMembershipGUI extends JFrame {
    private GymMembershipManager manager;
    private DefaultListModel<String> memberListModel;

    public GymMembershipGUI(GymMembershipManager manager) {
        this.manager = manager;
        this.memberListModel = new DefaultListModel<>();

        setTitle("체육관 회원 관리 시스템");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JList<String> memberList = new JList<>(memberListModel);
        JScrollPane scrollPane = new JScrollPane(memberList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("회원 추가");
        JButton deleteButton = new JButton("회원 삭제");
        JButton listButton = new JButton("회원 목록");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(listButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GymMembershipManager manager = new GymMembershipManager();
            GymMembershipGUI gui = new GymMembershipGUI(manager);
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
