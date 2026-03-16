@echo off
setlocal enabledelayedexpansion

set "INDIR=music"
set "INPUT=%INDIR%\fullsong.mp3"
set "OUTDIR=music"

if not exist "%OUTDIR%" mkdir "%OUTDIR%"

set "PREV_TIME="
set "PREV_TITLE="

for /f "tokens=1,*" %%A in (chapters.txt) do (
    set "CUR_TIME=%%A"
    set "CUR_TITLE=%%B"

    if defined PREV_TIME (
        echo Creating: !PREV_TITLE!.mp3
        ffmpeg.exe -y -i "%INPUT%" -ss !PREV_TIME! -to !CUR_TIME! -c copy "%OUTDIR%\!PREV_TITLE!.mp3"
    )

    set "PREV_TIME=!CUR_TIME!"
    set "PREV_TITLE=!CUR_TITLE!"
)

REM Handle last song (till end of file)
if defined PREV_TIME (
    echo Creating: !PREV_TITLE!.mp3
    ffmpeg.exe -y -i "%INPUT%" -ss !PREV_TIME! -c copy "%OUTDIR%\!PREV_TITLE!.mp3"
)

echo Done. Files saved in "%OUTDIR%" folder.
pause
