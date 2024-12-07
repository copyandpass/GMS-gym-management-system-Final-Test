import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 공지사항 관리 시스템을 위한 GUI 클래스입니다.
 * 사용자에게 공지사항을 추가, 수정, 삭제하는 기능을 제공합니다.
 */
public class NoticeManagementSystemGUI {
    private JFrame frame;
    private JTextArea textArea;
    private JTextField textFieldTitle;
    private List<Notice> noticeList;
    private NoticeDAO noticeDAO;

    /**
     * 프로그램을 초기화합니다.
     * GUI 구성 요소를 설정하고 공지사항 목록을 로드합니다.
     */
    public NoticeManagementSystemGUI() {
        noticeDAO = new NoticeDAO();
        noticeList = noticeDAO.getAllNotices();
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
            Notice notice = new Notice(title);
            noticeDAO.addNotice(notice);
            noticeList.add(notice);
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
        int selectedIndex = textArea.getCaretPosition();
        if (selectedIndex >= 0 && selectedIndex < noticeList.size()) {
            Notice notice = noticeList.get(selectedIndex);
            notice.setTitle(title);
            noticeDAO.updateNotice(notice);
            loadNotices();
        } else {
            JOptionPane.showMessageDialog(frame, "수정할 공지사항을 선택하세요.");
        }
    }

    /**
     * 공지사항을 삭제합니다.
     */
    private void deleteNotice() {
        int selectedIndex = textArea.getCaretPosition();
        if (selectedIndex >= 0 && selectedIndex < noticeList.size()) {
            Notice notice = noticeList.get(selectedIndex);
            noticeDAO.deleteNotice(notice);
            noticeList.remove(selectedIndex);
            loadNotices();
        } else {
            JOptionPane.showMessageDialog(frame, "삭제할 공지사항을 선택하세요.");
        }
    }

    /**
     * 공지사항 목록을 화면에 불러옵니다.
     */
    private void loadNotices() {
        textArea.setText("");
        for (Notice notice : noticeList) {
            textArea.append(notice.getTitle() + "\n");
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
