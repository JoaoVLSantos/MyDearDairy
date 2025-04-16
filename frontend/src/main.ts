import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';  
import { importProvidersFrom } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),  // Usando a constante de rotas importada
    importProvidersFrom(HttpClientModule)  // Adicionando o HttpClientModule como provedor
  ]
}).catch((err) => console.error(err));

