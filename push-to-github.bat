@echo off
chcp 65001 >nul
title 推送 组员B 代码到 GitHub

echo ============================================
echo   组员B — 统计与数据分析 ^| 推送到 GitHub
echo ============================================
echo.

REM === 请修改为你自己的信息 ===
set GIT_USER=yangyang6-6-6
set GIT_EMAIL=yangyang6-6-6@users.noreply.github.com
set REPO_URL=https://github.com/yangyang6-6-6/Java-.git

echo [1/5] 配置 Git 用户信息...
git config user.name "%GIT_USER%"
git config user.email "%GIT_EMAIL%"
if %ERRORLEVEL% neq 0 (
    echo [!] Git 未安装或无法运行
    pause
    exit /b 1
)
echo     user.name = %GIT_USER%
echo     user.email = %GIT_EMAIL%
echo.

echo [2/5] 添加所有文件到暂存区...
git add -A
if %ERRORLEVEL% neq 0 (
    echo [!] git add 失败
    pause
    exit /b 1
)
echo.

echo [3/5] 查看待提交文件...
git status
echo.

echo [4/5] 提交代码...
git commit -m "组员B-统计与数据分析：首次提交"
if %ERRORLEVEL% neq 0 (
    echo [!] git commit 失败（可能没有变更可提交）
    pause
    exit /b 1
)
echo.

echo [5/5] 推送到 GitHub...
git remote add origin %REPO_URL% 2>nul
git branch -M main
git push -u origin main

if %ERRORLEVEL% equ 0 (
    echo.
    echo ============================================
    echo   ✅ 推送成功！
    echo   仓库: %REPO_URL%
    echo ============================================
) else (
    echo.
    echo ============================================
    echo   ❌ 推送失败
    echo   可能原因：
    echo   1. 网络无法访问 github.com（请检查代理/VPN）
    echo   2. 未配置 GitHub 认证（Token 或 SSH）
    echo.
    echo   如果提示认证问题，请配置 Token：
    echo   git remote set-url origin https://你的Token@github.com/yangyang6-6-6/Java-.git
    echo   然后重新运行本脚本
    echo ============================================
)

echo.
pause
