I want the capability for offline usage of the app. I also want a way to synchronize/export data. I plan to try this with Google Sheets first. SQL tables can be converted into spreadsheets fairly easily. The one issue I can imagine is id assignment, it may take a little extra work to get that right.

After looking into using Google Sheets for backup/sync, it seems not feasible. It will take some time to learn how to do the authentication and use API in kotlin without any helper libraries. There are helper libraries available for Java, that might be one way to go. But basically what I want is file synchronization and I found a more standard solution that should work. Google has an Auto Backup feature that should backup all the files by default, which includes the sqlite database used by room (in theory). 

One thing I'll need to learn is how to handle migrations when using Room. 

Here is XML for enabling and configuring Auto Backup:
https://developer.android.com/guide/topics/data/autobackup
```
<manifest ... >
    ...
    <application android:allowBackup="true" ... >
        ...
    </application>
</manifest>


<?xml version="1.0" encoding="utf-8"?>
<data-extraction-rules>
 <cloud-backup [disableIfNoEncryptionCapabilities="true|false"]>
   <include domain="sharedpref" path="."/>
   <exclude domain="sharedpref" path="device.xml"/>
 </cloud-backup>
</data-extraction-rules>


<data-extraction-rules>
  <cloud-backup [disableIfNoEncryptionCapabilities="true|false"]>
    ...
    <include domain=["file" | "database" | "sharedpref" | "external" |
                        "root"] path="string"/>
    ...
    <exclude domain=["file" | "database" | "sharedpref" | "external" |
                        "root"] path="string"/>
    ...
  </cloud-backup>
  <device-transfer>
    ...
    <include domain=["file" | "database" | "sharedpref" | "external" |
                        "root"] path="string"/>
    ...
    <exclude domain=["file" | "database" | "sharedpref" | "external" |
                        "root"] path="string"/>
    ...
  </device-transfer>
</data-extraction-rules>

```