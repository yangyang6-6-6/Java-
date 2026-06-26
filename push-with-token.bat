@echo off
chcp 65001 >nul
title 组员B 推送脚本（Token 方式）

echo ============================================
echo   组员B — 统计与数据分析
echo   推送方式：Personal Access Token
echo ============================================
echo.

REM ==== 请修改以下信息 ====
set GIT_USER=yangyang6-6-6
set GIT_EMAIL=yangyang6-6-6@users.noreply.github.com
set GITHUB_USER=yangyang6-6-6
set REPO_NAME=Java-
echo.

REM 询问 Token
set /p TOKEN=请输入你的 GitHub Personal Access Token :

set REPO_URL=https://%GITHUB_USER%:%TOKEN%@github.com/%GITHUB_USER%/%REPO_NAME%.git

echo.
echo [1/4] 配置 Git 用户信息...
git config user.name "%GIT_USER%"
git config user.email "%GIT_EMAIL%"
echo.

echo [2/4] 添加文件...
git add -A
echo.

echo [3/4] 提交代码...
git commit -m "组员B-统计与数据分析：首次提交"
if %ERRORLEVEL% neq 0 (
    echo [!] 没有变更要提交或提交失败
    pause
    exit /b 1
)
echo.

echo [4/4] 推送到 GitHub（使用 Token 认证）...
git remote add origin %REPO_URL% 2>nul
git branch -M main
git push -u origin main

if %ERRORLEVEL% equ 0 (
    echo.
    echo ============================================
    echo   ✅ 推送成功！
    echo   仓库: https://github.com/%GITHUB_USER%/%REPO_NAME%
    echo ============================================
) else (
    echo.
    echo ============================================
    echo   ❌ 推送失败
    echo   请检查：
    echo   1. Token 是否正确（需要 repo 权限）
    echo   2. 网络是否能访问 github.com
    echo   3. 仓库 %REPO_NAME% 是否存在
    echo ============================================
)

echo.
pause
