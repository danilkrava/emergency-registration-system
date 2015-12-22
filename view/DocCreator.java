package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import model.Report;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;

public class DocCreator {

    public static void createDocFile(String fileName, List<Report> list) throws IOException {
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
        XWPFDocument doc = new XWPFDocument();

        XWPFTable table = doc.createTable(list.size()+1, 4);
        table.getRow(1).getCell(1).setText("id");
        table.getRow(1).getCell(2).setText("Дата");
        table.getRow(1).getCell(3).setText("Рівень радіації");
        table.getRow(1).getCell(4).setText("Інформація");

        for (int i = 0; i<list.size(); i++) {
            table.getRow(i+2).getCell(1).setText(Integer.toString(list.get(i).getId()));
            table.getRow(i+2).getCell(2).setText(list.get(i).getDate().toString());
            table.getRow(i+2).getCell(3).setText(Double.toString(list.get(i).getRadiation()));
            table.getRow(i+2).getCell(4).setText(list.get(i).getInfo());
        }


        //XWPFParagraph tempParagraph = doc.createParagraph();
            /*XWPFRun tempRun = tempParagraph.createRun();

            tempRun.setText("This is a Paragraph");
            tempRun.setFontSize(12);*/

        doc.write(fos);
        fos.close();

    }
}
