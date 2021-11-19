package com.dim.RestaurantManager.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.UUID;

public class QRGenerator {
    private String currentCode;

    private void generateQRImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
    private void generateQRToResources(String text, int width, int height) throws WriterException, IOException {
        this.currentCode = text;
        generateQRImage(text, width, height, ".\\src\\main\\resources\\static\\entry\\QR.PNG");
    }

    public void generateNewRandom() throws IOException, WriterException {
        this.generateQRToResources(UUID.randomUUID().toString(), 500, 500);
    }

    public String getCurrentCode() {
        return currentCode;
    }


}
