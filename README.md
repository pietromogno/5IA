# 5IA
# Pagotto Emanuele


# Risponditore
Il progetto risponditore è costituito da una classe client, da una classe server e da una classe risponditore che lavora assieme al server per fornire una conversazione con il client.

Il risponditore è stato costruito con un automa a stati finiti, rappresentato concettualmente da un grafo orientato.
La rappresentazione dell'automa nel codice è creata usando un array bidimensionale di oggetti Node.
Node è una classe annidata in Risponditore, in cui è contenuto il messaggio da presentare come domanda nello stato dell'automa, e l'array delle risposte disponibili.
