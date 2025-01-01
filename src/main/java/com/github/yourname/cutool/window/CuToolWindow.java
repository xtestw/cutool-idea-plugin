package com.github.yourname.cutool.window;

import com.intellij.openapi.project.Project;
import com.intellij.ui.jcef.JBCefApp;
import com.intellij.ui.jcef.JBCefBrowser;
import com.intellij.ui.jcef.JBCefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefLifeSpanHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.Desktop;
import java.net.URI;
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
                
                // 设置链接处理
                JBCefClient client = browser.getJBCefClient();
                client.addLifeSpanHandler(new CefLifeSpanHandler() {
                    @Override
                   public boolean onBeforePopup(
                            CefBrowser browser, CefFrame frame, String targetUrl, String target_frame_name){
                        openInExternalBrowser(targetUrl);
                        return true; // 阻止在JCEF中打开新窗口
                    }


                    @Override
                    public void onAfterCreated(CefBrowser browser) {}

                    @Override
                    public void onBeforeClose(CefBrowser browser) {}

                    @Override
                    public boolean doClose(CefBrowser browser) {
                        return false;
                    }

                    @Override
                    public void onAfterParentChanged(CefBrowser browser) {}
                }, browser.getCefBrowser());
                
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

    private void openInExternalBrowser(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
            showError("无法打开外部浏览器: " + e.getMessage());
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