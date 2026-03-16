@echo off
setlocal enabledelayedexpansion

set inputFile=urls.txt
set outDir=music

if not exist "%outDir%" mkdir "%outDir%"

for /f "usebackq delims=" %%A in ("%inputFile%") do (
    echo Processing URL: %%A
    yt-dlp -x --audio-format mp3 --no-playlist -o "%outDir%\%%(title)s.%%(ext)s" "%%A"
    if errorlevel 1 (
        echo Failed to process URL: %%A
    ) else (
        echo Successfully processed URL: %%A
    )
)

echo All URLs processed
pause
