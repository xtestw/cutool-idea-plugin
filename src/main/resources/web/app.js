document.addEventListener('DOMContentLoaded', () => {
    // 为所有tab按钮添加点击事件
    document.querySelectorAll('.tab-button').forEach(button => {
        button.addEventListener('click', () => {
            const tabId = button.getAttribute('data-tab');
            showTab(tabId);
        });
    });

    // 默认显示第一个tab
    showTab('jsonFormatter');
});

function showTab(tabId) {
    // 移除所有tab的active类
    document.querySelectorAll('.tab-button').forEach(button => {
        button.classList.remove('active');
    });
    document.querySelectorAll('.tab-content').forEach(content => {
        content.classList.remove('active');
    });
    
    // 添加active类到选中的tab
    document.querySelector(`[data-tab="${tabId}"]`).classList.add('active');
    document.getElementById(tabId).classList.add('active');
}

function formatJson() {
    try {
        const input = document.getElementById('jsonInput').value;
        const parsed = JSON.parse(input);
        document.getElementById('jsonOutput').textContent = 
            JSON.stringify(parsed, null, 2);
    } catch (e) {
        document.getElementById('jsonOutput').textContent = 
            'Invalid JSON: ' + e.message;
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