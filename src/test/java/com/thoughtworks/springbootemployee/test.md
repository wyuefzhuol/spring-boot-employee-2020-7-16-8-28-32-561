##  obtain company list

**given**
    2 companies

**when**
    get companies
    
**then**
    return 2 companies    
---  
 
 **given**
     0 companies
 
 **when**
     get companies
     
 **then**
     return 0 companies
---

##  obtain a certain specific company

**given**
    company id 1
    companies have company with id 1

**when**
    get companies by id
    
**then**
    return 1 company with id 1
---

**given**
    company id 1
    companies don't have company with id 1

**when**
    get companies by id
    
**then**
    return 0 companies
---