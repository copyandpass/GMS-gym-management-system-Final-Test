import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * 공지사항 관리 시스템을 위한 GUI 클래스입니다.
 * 사용자에게 공지사항을 추가, 수정, 삭제하는 기능을 제공합니다.
 */
public class NoticeManagementSystemGUI {
    private JFrame frame;
    private JTextArea textArea;
    private JTextField textFieldTitle;
    private Map<Integer, Notice> noticeMap;
    private int currentId;

    /**
     * 프로그램을 초기화합니다.
     * GUI 구성 요소를 설정하고 공지사항 목록을 초기화합니다.
     */
    public NoticeManagementSystemGUI() {
        noticeMap = new HashMap<>();
        currentId = 1; // 공지사항 ID 초기값
        initialize();
    }

    /**
     * GUI 화면을 초기화합니다.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.NORTH);

        JLabel lblTitle = new JLabel("공지사항 제목:");
        panel.add(lblTitle);

        textFieldTitle = new JTextField();
        panel.add(textFieldTitle);
        textFieldTitle.setColumns(20);

        JButton btnAdd = new JButton("공지사항 추가");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNotice();
            }
        });
        panel.add(btnAdd);

        JButton btnEdit = new JButton("공지사항 수정");
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editNotice();
            }
        });
        panel.add(btnEdit);

        JButton btnDelete = new JButton("공지사항 삭제");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteNotice();
            }
        });
        panel.add(btnDelete);

        textArea = new JTextArea();
        frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
        loadNotices();
    }

    /**
     * 공지사항을 추가합니다.
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
     * 공지사항을 수정합니다.
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
     * 공지사항을 삭제합니다.
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
     * 공지사항 목록을 화면에 불러옵니다.
     */
    private void loadNotices() {
        textArea.setText("");
        for (Notice notice : noticeMap.values()) {
            textArea.append("ID: " + notice.getId() + " | 제목: " + notice.getTitle() + "\n");
        }
    }

    /**
     * 프로그램을 실행합니다.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    NoticeManagementSystemGUI window = new NoticeManagementSystemGUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

/**
 * 공지사항 객체를 나타내는 클래스입니다.
 */
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
