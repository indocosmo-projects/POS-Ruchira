<xpml><page quantity='0' pitch='60.1 mm'></xpml>'Seagull:2.1:DP
INPUT OFF
VERBOFF
INPUT ON
SYSVAR(48) = 0
ERROR 15,"FONT NOT FOUND"
ERROR 18,"DISK FULL"
ERROR 26,"PARAMETER TOO LARGE"
ERROR 27,"PARAMETER TOO SMALL"
ERROR 37,"CUTTER DEVICE NOT FOUND"
ERROR 1003,"FIELD OUT OF LABEL"
SYSVAR(35)=0
OPEN "tmp:setup.sys" FOR OUTPUT AS #1
PRINT#1,"Printing,Media,Print Area,Media Margin (X),0"
PRINT#1,"Printing,Media,Clip Default,On"
CLOSE #1
SETUP "tmp:setup.sys"
KILL "tmp:setup.sys"
CLIP ON
CLIP BARCODE ON
LBLCOND 3,2
<xpml></page></xpml><xpml><page quantity='1' pitch='60.1 mm'></xpml>CLL
OPTIMIZE "BATCH" ON
LAYOUT INPUT "c:SSFMT021.LAY"
AN7
DIR4
NASC 8
PP2,120:FT "CG Triumvirate Condensed Bold",14,0,99
PT "${COMPANY}"
PP41,100:FT "Univers Condensed Bold",8,0,99
PT "Nutritional Facts (Per 50 Gms)"

PP72,40:PT "Particulars"
PP72,285:PT "Wt"
PP72,370:PT "Daily"

PP72,10:PL450,3
PP102,10:PL450,3
PP102,10:PL3,33
PP102,270:PL3,33
PP102,355:PL3,33
PP102,457:PL3,33

${SECTIONA}

${SECTIONB}

PP${INGROW1},25:FT "Univers Condensed Bold",12,0,99
PT "Ingredients"
PP${INGROW2},25:FT "Univers Condensed Bold",8,0,99
PT "${INGREDIENTS}"
PP${REMROW1},25:PT "${REMARKS1}"
PP${REMROW2},25:PT "${REMARKS2}"

PP580,80:BARSET "CODE93",2,1,3,50
PB "${BARCODE}"
PP625,165:FT "Univers Condensed Bold",10,0,99
PT "${BARCODE}"
PP655,25:FT "CG Triumvirate Condensed Bold",10,0,99
PT "${ITEMNAME}"
PP685,25:FT "CG Triumvirate Condensed Bold",10,0,99
PT "MRP: ${UNITPRICE}"
PP720,25:FT "Univers Condensed Bold",8,0,99
PT "PKD ON: ${PACKEDON} NET WT: ${NETWEIGHT}"
PP745,25:PT "Best Before: ${BESTBEFORE} from date of packing"
PP73,16:AN1

LAYOUT END
LAYOUT RUN "c:SSFMT021.LAY"
PF${COUNT}
PRINT KEY OFF
<xpml></page></xpml>LAYOUT RUN ""
<xpml><end/></xpml>
