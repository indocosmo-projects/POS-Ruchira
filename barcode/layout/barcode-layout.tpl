SETUP"Printing,Media,Start Adjust,-136"
SETUP"Printing,Media,Stop Adjust,0"
SETUP"Printing,Print Quality,Print Speed,75"
SETUP"Printing,Print Quality,Darkness,80"
SETUP"Printing,Print Area,Media Width,800"

SYSVAR(57)=0
CLL
OPTIMIZE "BATCH" ON
qXPos% = 0
PP204+qXPos%,163:AN7
NASC 8

PP71+qXPos%,251:BARSET "CODE128",2,1,3,56
PB "${BARCODE}"
PP146+qXPos%,197:FT "Univers Condensed Bold",8,0,99
PT "${BARCODE}"
PP30+qXPos%,174:FT "CG Triumvirate Condensed Bold",8,0,99
PT "${ITEMNAME}"
PP30+qXPos%,151:FT "CG Triumvirate Condensed Bold",10,0,99
PT "MRP : ${UNITPRICE}"
PP188+qXPos%,141:FT "Univers Condensed Bold",6,0,99
PT "(Incl of all Taxes)"
FT "Univers Condensed Bold",8,0,99
PP222+qXPos%,113:PT "NET WT :${NETWEIGHT}"
PP30+qXPos%,116:FT "Univers Condensed Bold",8,0,99
PT "PKD ON :${PACKEDON}"
PP30+qXPos%,95:FT "Univers Condensed Bold",8,0,99
PT "Best Before:${BESTBEFORE}"
PP28+qXPos%,68:FT "Univers Condensed Bold",7,0,99
PT "License NO:1131207001000"
PP28+qXPos%,48:FT "Univers Condensed Bold",7,0,99
PT "MFD BY:BEST PRODUCTS"
PP28+qXPos%,25:FT "Univers Condensed Bold",7,0,99
PT "CUSTOMER CARE NO :9446818355"

qXPos% = 400

PP71+qXPos%,251:BARSET "CODE128",2,1,3,56
PB "${BARCODE}"
PP146+qXPos%,197:FT "Univers Condensed Bold",8,0,99
PT "${BARCODE}"
PP30+qXPos%,174:FT "CG Triumvirate Condensed Bold",8,0,99
PT "${ITEMNAME}"
PP30+qXPos%,151:FT "CG Triumvirate Condensed Bold",10,0,99
PT "MRP : ${UNITPRICE}"
PP188+qXPos%,141:FT "Univers Condensed Bold",6,0,99
PT "(Incl of all Taxes)"
FT "Univers Condensed Bold",8,0,99
PP222+qXPos%,113:PT "NET WT :${NETWEIGHT}"
PP30+qXPos%,116:FT "Univers Condensed Bold",8,0,99
PT "PKD ON :${PACKEDON}"
PP30+qXPos%,95:FT "Univers Condensed Bold",8,0,99
PT "Best Before:${BESTBEFORE}"
PP28+qXPos%,68:FT "Univers Condensed Bold",7,0,99
PT "License NO:1131207001000"
PP28+qXPos%,48:FT "Univers Condensed Bold",7,0,99
PT "MFD BY:BEST PRODUCTS"
PP28+qXPos%,25:FT "Univers Condensed Bold",7,0,99
PT "CUSTOMER CARE NO :9446818355"

LAYOUT RUN ""
PF${COUNT}

