import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * 공지사항 관리 시스템 GUI 클래스.
 * 사용자가 공지사항을 추가, 수정, 삭제할 수 있는 기능을 제공합니다.
 */
public class NoticeManagementSystemGUI {
    private JFrame frame;         // 메인 프레임
    private JTextArea textArea;   // 공지사항 표시 영역
    private JTextField textFieldTitle; // 공지사항 제목 입력 필드
    private Map<Integer, Notice> noticeMap; // 공지사항 저장용 맵
    private int currentId;        // 공지사항 ID 관리

    /**
     * 프로그램 초기화: 공지사항 데이터 초기화 및 GUI 구성.
     */
    public NoticeManagementSystemGUI() {
        noticeMap = new HashMap<>();
        currentId = 1; // 공지사항 ID 시작값 설정
        initialize();
    }

    /**
     * GUI 화면 초기화: 프레임, 패널, 버튼, 텍스트 영역 구성.
     */
    private void initialize() {
        frame = new JFrame("공지사항 관리 시스템");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // 상단 패널 구성
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.NORTH);

        panel.add(new JLabel("공지사항 제목:"));

        textFieldTitle = new JTextField(20);
        panel.add(textFieldTitle);

        // 공지사항 추가 버튼
        JButton btnAdd = new JButton("공지사항 추가");
        btnAdd.addActionListener(e -> addNotice());
        panel.add(btnAdd);

        // 공지사항 수정 버튼
        JButton btnEdit = new JButton("공지사항 수정");
        btnEdit.addActionListener(e -> editNotice());
        panel.add(btnEdit);

        // 공지사항 삭제 버튼
        JButton btnDelete = new JButton("공지사항 삭제");
        btnDelete.addActionListener(e -> deleteNotice());
        panel.add(btnDelete);

        // 중앙 공지사항 표시 영역
        textArea = new JTextArea();
        frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
        loadNotices();
    }

    /**
     * 공지사항 추가 기능: 제목을 입력받아 새로운 공지사항을 저장.
     */
    private void addNotice() {
        String title = textFieldTitle.getText();
        if (!title.isEmpty()) {
            Notice notice = new Notice(currentId, title);
            noticeMap.put(currentId, notice);
            currentId++;
            loadNotices();
        } else {
            JOptionPane.showMessageDialog(frame, "제목을 입력하세요.");
        }
    }

    /**
     * 공지사항 수정 기능: ID를 입력받아 제목 수정.
     */
    private void editNotice() {
        String title = textFieldTitle.getText();
        String inputId = JOptionPane.showInputDialog(frame, "수정할 공지사항 ID를 입력하세요:");
        try {
            int id = Integer.parseInt(inputId);
            if (noticeMap.containsKey(id)) {
                Notice notice = noticeMap.get(id);
                notice.setTitle(title);
                loadNotices();
            } else {
                JOptionPane.showMessageDialog(frame, "해당 ID의 공지사항이 없습니다.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "유효한 숫자를 입력하세요.");
        }
    }

    /**
     * 공지사항 삭제 기능: ID를 입력받아 해당 공지사항 제거.
     */
    private void deleteNotice() {
        String inputId = JOptionPane.showInputDialog(frame, "삭제할 공지사항 ID를 입력하세요:");
        try {
            int id = Integer.parseInt(inputId);
            if (noticeMap.containsKey(id)) {
                noticeMap.remove(id);
                loadNotices();
            } else {
                JOptionPane.showMessageDialog(frame, "해당 ID의 공지사항이 없습니다.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "유효한 숫자를 입력하세요.");
        }
    }

    /**
     * 공지사항 목록 갱신: 저장된 공지사항을 텍스트 영역에 출력.
     */
    private void loadNotices() {
        textArea.setText("");
        for (Notice notice : noticeMap.values()) {
            textArea.append("ID: " + notice.getId() + " | 제목: " + notice.getTitle() + "\n");
        }
    }

    /**
     * 프로그램 실행 메서드: GUI를 표시.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                NoticeManagementSystemGUI window = new NoticeManagementSystemGUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

/**
 * 공지사항 객체를 나타내는 클래스.
 */
class Notice {
    private int id;           // 공지사항 ID
    private String title;     // 공지사항 제목

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
