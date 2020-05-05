<div align="center"><b style="font-size:30px"><u>Bases de Datos 2 2020 -TP2</u></b></div>
<div align="center"><b style="font-size:25px"><u>Bases de Datos NoSQL / Práctica con MongoDB</u></b></div>
<div align="center"><emp style="font-size:20px">Entrega: 11/5</emp></div>

---

### Parte 1: Bases de Datos NoSQL y Relacionales

---

### Parte 2: Primeros pasos con MongoDB

5. Crear la base de datos solicitada y agregar un documento a una coleccion nueva:
```
use airbdb
db.apartments.insetOne({name:'Apartment with 2 bedrooms', capacity:4})
```
Al usar "db.apartments.find()" se puede apreciar que hay un atributo más que los que se insertó anteriormente con nombre _id que es un identificador que mongo generó automaticamente al crear el documento.

6. Agregar muchos documentos a una coleccion:
```
db.apartments.insertMany([{name:'New Apartment', capacity:3, services: ['wifi', 'ac']}, {name:'Nice apt for 6', capacity:6, services: ['parking']}, {name:'1950s Apartment', capacity:3}, {name:'Duplex Floor', capacity:4, services: ['wifi', 'breakfast', 'laundry']}])
```
Busquedas solicitadas:
	+ "db.apartments.find({capacity:3})"
	+ "db.apartments.find({capacity:{$gte:4}})"
	+ "db.apartments.find({$and:[{services:"wifi"},{name:{$regex: /Apartment$/} }]})"
	+ "db.apartments.find({$and: [{capacity:{$gt:3}},{name:{$regex: /Apartment$/ }}]})"
	+ "db.apartments.find({services:null})"

Aplicando la proyeccion quedaria :"db.apartments.find({services:null}, {name :1, _id:0})"

7. Aumenta la capacidad del departarmento solicitado:
"db.apartments.updateOne({name:"Duplex Floor"}, {$set:{capacity:5}})"

8. Agrega un servicio al departamento solicitado:
"db.apartments.updateOne({name:"Nice apt for 6"}, {$addToSet:{services:"laundry"}})"

9. Incrementar la capacidad de todos los departamentos que en sus servicios tengan wifi:
"db.apartments.update({services:{$in:["wifi"]}}, {$inc: {capacity:1}})"

---