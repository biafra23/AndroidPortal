# AndroidPortal

This app integrates [Trueblocks](https://github.com/biafra23/trueblocks-kotlin) and the Portal Client [Samba](https://github.com/meldsun0/Samba) via [SambaWraopper](https://github.com/biafra23/SambaWrapper) and shows their respective usage.

## How to use the app

### Requirements

The app needs `Android 15` (because some of its dependencies use Java-APIs that are only available in Androdi 15). 

### Usage
1. Enter your account in the top EditText field. An example is provided. Ideally this Address should have very recent transactions and also transactions before block 12Mio.
2. Hi the Button `Check address with Trueblocks` and be patient. You should see bloch number / tx Index pairs in the logbox at the bottom.
3. Use one of these pairs and put it in the second EditText field and search for those transactions. Usually the first try times out and doesn't give a result. Wait a fey seconds and hit the button again. Then you should see the txHash in the Box below.
