import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import routes from './app.routes'; // Certifique-se de que o caminho de importação está correto

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }), // Habilita a detecção de mudanças com coalescência de eventos
    provideRouter(routes) // Fornece o roteador com as rotas definidas
  ]
};


