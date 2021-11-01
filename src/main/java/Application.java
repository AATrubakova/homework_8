import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Application extends JFrame {
    private String path1;
    private String path2;

    ArrayList<String> pathsToHandle = new ArrayList<>();

    public Application() {
        Component component = this;

        setTitle("First Application");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 300, 200, 200);
        setLayout(new GridLayout(1, 3));

        JButton buttonHandleThis = new JButton("-");
        buttonHandleThis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handlePdf(path1, path2);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton buttonFileExplorer1 = new JButton("Директория");
        buttonFileExplorer1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF files", "pdf");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(component);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    path1 = selectedFile.getAbsolutePath();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                }
            }
        });

        JButton buttonFileExplorer2 = new JButton("2 файл");
        buttonFileExplorer2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(component);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    path2 = selectedFile.getAbsolutePath();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                }
            }
        });

        add(buttonHandleThis);
        add(buttonFileExplorer1);
        add(buttonFileExplorer2);

        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new Application();
    }

    public static void handlePdf(String path1, String path2) throws IOException {
        File directory = new File(path1);
        File[] pdfFiles = directory.listFiles();

        File file2 = new File(path2);

        for (File file1 : pdfFiles) {
            PDDocument document1 = Loader.loadPDF(file1);
            PDDocument document2 = Loader.loadPDF(file2);

            PDPageTree pages = document2.getPages();

            for (PDPage page : pages) {
                document1.addPage(page);
            }
            System.out.println("Pdf loaded");

            document1.save(file1.getAbsolutePath());
            document1.close();
            document2.close();
        }

    }
}
