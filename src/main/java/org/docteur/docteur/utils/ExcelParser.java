package org.docteur.docteur.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.docteur.docteur.models.PatientSymptome;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelParser {

    public static List<PatientSymptome> getPatientSymptomeFromExcel(Object fileData, Long idPatient, LocalDateTime dateConsultation) throws Exception {
        FileInputStream file;
        if(fileData instanceof String)
            file = new FileInputStream(fileData.toString());

        else if(fileData instanceof File)
            file = new FileInputStream((File)fileData);

        else
            throw new Exception("fileData incorrect : "+fileData.getClass().getName());

        Workbook workbook = new XSSFWorkbook(file);
        List<PatientSymptome> patientSymptomeList = new ArrayList<>();

        try (workbook) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            Row row;
            itr.next();
            PatientSymptome patientSymptome;
            int[] colonnes;
            Iterator<Cell> cellIterator;
            Cell cell;
            double nbr;
            while (itr.hasNext()) {
                row = itr.next();
                cellIterator = row.cellIterator();
                colonnes = new int[2];
                for (int j = 0; cellIterator.hasNext(); j++) {
                    cell = cellIterator.next();
                    if (cell.getCellType() == CellType.NUMERIC) {
                        nbr = cell.getNumericCellValue();
                        colonnes[j] = (int) nbr;
                    }
                }
                if (colonnes[0] < 0 || colonnes[1] < 0)
                    throw new Exception("Valeur négative : "+colonnes[0] +" ou "+colonnes[1]);
                patientSymptome = new PatientSymptome(idPatient, (long)(colonnes[0]), colonnes[1], dateConsultation);
                patientSymptomeList.add(patientSymptome);
            }
        }
        return patientSymptomeList;
    }

}
