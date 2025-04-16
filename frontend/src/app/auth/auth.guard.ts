import { CanActivateFn, Router, ActivatedRouteSnapshot } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from './auth.sevice';

export const authGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const authService = inject(AuthService);  // Injeta o serviço de autenticação
  const router = inject(Router);  // Injeta o router

  // Verifica se o usuário está autenticado
  if (!authService.isAuthenticated()) {
    return router.createUrlTree(['/login']);  // Redireciona para a página de login se não autenticado
  }

  // Se o usuário estiver autenticado, permite o acesso à rota
  return true;
};

