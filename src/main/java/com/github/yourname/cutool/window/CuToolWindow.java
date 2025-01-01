package com.github.yourname.cutool.window;

import com.intellij.openapi.project.Project;
import com.intellij.ui.jcef.JBCefApp;
import com.intellij.ui.jcef.JBCefBrowser;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class CuToolWindow {
    private final JPanel content;
    private JBCefBrowser browser;

    public CuToolWindow(Project project) {
        content = new JPanel(new BorderLayout());
        
        if (JBCefApp.isSupported()) {
            try {
                // 创建JCEF浏览器实例
                browser = new JBCefBrowser();
                content.add(browser.getComponent(), BorderLayout.CENTER);
                
                // 等待组件显示后再加载URL
                SwingUtilities.invokeLater(() -> {
                    try {
                        // 加载本地HTML文件
                        URL resourceUrl = this.getClass().getResource("/web/index.html");
                        if (resourceUrl != null) {
                            System.out.println("Loading URL: " + resourceUrl);
                            String baseUrl = resourceUrl.toExternalForm().replace("index.html", "");
                            browser.loadHTML(getIndexHtmlContent(), baseUrl);
                        } else {
                            System.out.println("Resource not found: web/index.html");
                            showError("无法加载资源: web/index.html");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showError("加载资源失败: " + e.getMessage());
                    }
                });
                
            } catch (Exception e) {
                e.printStackTrace();
                showError("浏览器组件初始化失败: " + e.getMessage());
            }
        } else {
            showError("当前环境不支持JCEF浏览器");
        }
    }

    private String getIndexHtmlContent() {
        try {
            URL url = getClass().getResource("/web/index.html");
            if (url != null) {
                return new String(url.openStream().readAllBytes(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<html><body><h1>Error loading content</h1></body></html>";
    }

    private void showError(String message) {
        SwingUtilities.invokeLater(() -> {
            JLabel errorLabel = new JLabel(message, SwingConstants.CENTER);
            content.removeAll();
            content.add(errorLabel, BorderLayout.CENTER);
            content.revalidate();
            content.repaint();
        });
    }

    public JComponent getContent() {
        return content;
    }
} 