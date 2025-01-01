function showTab(tabId) {
    // 隐藏所有tab内容
    document.querySelectorAll('.tab-content').forEach(tab => {
        tab.style.display = 'none';
    });
    
    // 显示选中的tab
    document.getElementById(tabId).style.display = 'block';
}

function formatJson() {
    try {
        const input = document.getElementById('jsonInput').value;
        const parsed = JSON.parse(input);
        document.getElementById('jsonOutput').textContent = 
            JSON.stringify(parsed, null, 2);
    } catch (e) {
        document.getElementById('jsonOutput').textContent = 
            'Invalid JSON: ' + e.getMessage();
    }
}

function convertTimestamp() {
    const timestamp = document.getElementById('timestampInput').value;
    try {
        const date = new Date(parseInt(timestamp));
        document.getElementById('dateOutput').value = date.toLocaleString();
    } catch (e) {
        document.getElementById('dateOutput').value = '无效的时间戳';
    }
}

function getCurrentTimestamp() {
    document.getElementById('timestampInput').value = Date.now();
    convertTimestamp();
}

// 默认显示JSON格式化工具
document.addEventListener('DOMContentLoaded', () => {
    showTab('jsonFormatter');
}); 