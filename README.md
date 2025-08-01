# AndroidPortal

This app integrates [Trueblocks](https://github.com/biafra23/trueblocks-kotlin) and the Portal Client [Samba](https://github.com/meldsun0/Samba) via [SambaWraopper](https://github.com/biafra23/SambaWrapper) and shows their respective usage.

## How to use the app

### Requirements

The app needs `Android 15` (because some of its dependencies use Java-APIs that are only available in Androdi 15). 

### Usage
1. Enter your account in the top EditText field. An example is provided. Ideally this Address should have very recent transactions and also transactions before block 12Mio.
2. Hi the Button `Check address with Trueblocks` and be patient. You should see bloch number / tx Index pairs in the logbox at the bottom.
3. Use one of these pairs and put it in the second EditText field and search for those transactions. Usually the first try times out and doesn't give a result. Wait a fey seconds and hit the button again. Then you should see the txHash in the Box below.

### Limitations

Queries to Trueblocks take a long time. Especially if the address has no recent transactions. The Bloom filter match is doen in reverse order (newest blocks to oldest blocks).

At the moment only transactions in blocks before 12.799.946 can be found. Also the first tx search usually fails with an internal timeout in Samba and doesn't give a result. Please wait a few seconds and try again.  

### Options
The trueblocks-kotlin library alllows serching with a local IPFS client. By default an http gateway is used. 
When you want to use the local IPFS client you need to: 
- start it [here](https://github.com/biafra23/AndroidPortal/blob/82032518af12a58b0cb928e7ad0da438afd30af0/app/src/main/java/com/jaeckel/androidportal/AndroidPortalApplication.kt#L65-L74)
- use a IPFSLocalClient [here](https://github.com/biafra23/AndroidPortal/blob/82032518af12a58b0cb928e7ad0da438afd30af0/app/src/main/java/com/jaeckel/androidportal/MainActivity.kt#L376)
- Add Pinata as a peer [here](https://github.com/biafra23/AndroidPortal/blob/82032518af12a58b0cb928e7ad0da438afd30af0/app/src/main/java/com/jaeckel/androidportal/MainActivity.kt#L381-L383)
  
