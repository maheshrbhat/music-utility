@echo off
setlocal enabledelayedexpansion

:: Input File with URLs
set inputFile=urls.txt

for /f "usebackq delims=" %%A in ("%inputFile%") do (
		echo Processing URL: %%A
		yt-dlp -x --audio-format mp3 %%A
		if errorlevel 1 (
			echo Failed to process URL: %%A
			) else (
			echo Successfully processed URL: %%A
			)
)
echo All URLs processed
pause	