# To create an exe from the java jar
gradle clean build createExe

look intside build/launch4j/
currently putting the file inside
C:\Users\Justan\CustomApps

# To add to Windows Context Menu
1. Open regedit (search windows)
2. Find 'HKEY_CLASSES_ROOT/Directory/shell/'
3. Add a new key named 'Movie Report'
4. Select 'Movie Report' and add key named 'command'
5. set the 'data' to <path to>\MovieRename.exe "%1"

See: https://thegeekpage.com/add-any-program-to-right-click-context-menu/