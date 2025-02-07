
  const clone1=JSON.parse(JSON.stringify(person));
  const clone2=JSON.parse(JSON.stringify(person));
const samePerson=person;
  person.age=78;
  person.country='FR';
 console.log(clone1,clone2,samePerson,person)
  