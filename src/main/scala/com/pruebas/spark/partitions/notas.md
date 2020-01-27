Descargamos ficheros parquet de:
https://www.dropbox.com/s/nbjf6pnaxoy8udh/accounts.zip?dl=0 (pulsamos en open a la derecha y download)

Descomprimimos y lo subimos a hdfs (desde la termianl tras descomrpimir):
hdfs dfs -put accounts /user/hive/warehouse/

lanzamos en hue:

CREATE  TABLE accounts (
    acct_num INT,
    acct_create_dt TIMESTAMP,
    acct_close_dt  TIMESTAMP,
    first_name VARCHAR(255)  ,
    last_name VARCHAR(255) ,
    address  VARCHAR(255) ,
    city  VARCHAR(255) ,
    state VARCHAR(255) ,
    zipcode VARCHAR(255) ,
    phone_number VARCHAR(255) ,
    created TIMESTAMP  ,
    modified TIMESTAMP)
    ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ',';

Y comprobamos que hay datos:

SELECT * from accounts limit 10;
1	2008-10-23 16:05:05	NULL	Donald	Becton	2275 Washburn Street	Oakland	CA	94660	5100032418	2014-03-18 13:29:47	2014-03-18 13:29:47	
2	2008-11-12 03:00:01	NULL	Donna	Jones	3885 Elliott Street	San Francisco	CA	94171	4150835799	2014-03-18 13:29:47	2014-03-18 13:29:47	
3	2008-12-21 09:19:50	NULL	Dorthy	Chalmers	4073 Whaley Lane	San Mateo	CA	94479	6506877757	2014-03-18 13:29:47	2014-03-18 13:29:47	
4	2008-11-28 00:08:09	NULL	Leila	Spencer	1447 Ross Street	San Mateo	CA	94444	6503198619	2014-03-18 13:29:47	2014-03-18 13:29:47	
5	2008-11-15 23:06:06	NULL	Anita	Laughlin	2767 Hill Street	Richmond	CA	94872	5107754354	2014-03-18 13:29:47	2014-03-18 13:29:47	
6	2008-11-20 12:39:33	2014-03-01 07:37:48	Stevie	Bridge	3977 Linda Street	Sacramento	CA	94264	9162111862	2014-03-18 13:29:47	2014-03-18 13:29:47	
7	2008-12-09 10:32:12	2010-10-16 10:01:51	David	Eggers	2109 Ross Street	Oakland	CA	94508	5103935529	2014-03-18 13:29:47	2014-03-18 13:29:47	
8	2008-12-15 08:49:38	NULL	Dorothy	Koopman	1985 Pratt Avenue	San Mateo	CA	94469	6502406661	2014-03-18 13:29:47	2014-03-18 13:29:47	
9	2008-11-07 17:58:55	2014-02-14 01:26:52	Kara	Kohl	235 Fort Street	Palo Alto	CA	94312	6502384894	2014-03-18 13:29:47	2014-03-18 13:29:47	
10	2008-12-02 23:28:01	NULL	Diane	Nelson	921 Sardis Sta	Oakland	CA	94577	5102711264	2014-03-18 13:29:47	2014-03-18 13:29:47	
