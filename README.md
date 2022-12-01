# COSC 341 Group 3 - Budgeting App

## TODO

- [x] Dashboard - Rev
- [x] Envelopes - Brock
- [ ] Acccounts - Erfan
- [ ] Transactions - Esteban
  - [x] Read transactions
  - [x] Write transaction
    - [ ] Better Date picker
    - [ ] Better Amount picker
  - [ ] Edit/Delete transaction
- [ ] Reports - Rommel

## XML Struture

- Transactions {TransactionId (PK), EnvelopeId, Date, Note, Account, Amount} EnvelopeId is FK to Envelopes
- Envelopes {EnvelopeId (PK), Budget, Name, Color}

## Further Evaluation

- Meyz:
  - Add share/export pdf of reports
  - On Envelopes page, textview positions need to adjust.
  - On the Account page, change the buttons so that users can understand that they will
- Mahid
  - Add more details for the quick add button so that users understand what the button is for
  - On the transactions page, add a sort button based on different factors
- Sana:
  - On the new transactions, for the date, the drop-down menu is easier for the user
  - On the account page, there is no need for the show all button; the app can show all the transactions in a simple window with a scroll bar.
