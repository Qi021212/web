package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/captcha")
public class CaptchaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int width = 160,height = 40;
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        Random random = new Random();
        String captchaStr = "";
        for(int i=0;i<5;i++){
            char ch = (char)(random.nextInt(26)+65);//ASCII���ȡ��ĸ
            captchaStr += ch;
        }
        req.getSession().setAttribute("captcha",captchaStr);

        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Arial",Font.PLAIN,24));
        g.setColor(Color.BLACK);
        g.drawString(captchaStr, 20, 30);
        g.dispose();

        resp.setContentType("image/png");
        ImageIO.write(image,"png", resp.getOutputStream());
    }
}
