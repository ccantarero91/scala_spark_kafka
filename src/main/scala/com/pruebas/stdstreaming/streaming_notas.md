#generamos el direcotrio tempora para la salida
mkdir -p /tmp/devsh-streaming
chmod +wr /tmp/devsh-streaming


#para generar los datos cada segundo
cd /home/cloudera/training_materials/devsh/scripts/
./streamtest-file.sh ../data/activations_stream/ /tmp/devsh-streaming/

#ahora ya deberiamos de ver la salida

#topic con kafka y spark
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic events
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic notifications
#comprobamos que existen
kafka-topics --list --zookeeper localhost:2181
#lanzamos un consumidor para eventos
kafka-console-consumer --bootstrap-server localhost:9092 --topic events --from-beginning
#ahora lo probamos desde el JMeter (dia_4_kafka.jmx ) => vemos los 50 mensajes
#ahora cambiamos en JMeter para poner mensajes personalizados (dia_4_kafka_complejo.jmx)
{
        "tx_id":{{SEQUENCE("tx_id", 1, 1)}},
        "tx_card_type":"{{RANDOM_STRING("Finanzas", "Seguros", "Salud", "Logistica")}}",
        "tx_amount":"{{RANDOM_INT_RANGE(100, 40000)}}",
        "tx_datetime":{{TIMESTAMP()}}
}
