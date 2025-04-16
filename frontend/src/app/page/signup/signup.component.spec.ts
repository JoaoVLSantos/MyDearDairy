import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { SignupComponent } from './signup.component';  // Importando o componente standalone
import { RegistreService } from '../../service/registeService';
import { of, throwError } from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('SignupComponent', () => {
  let component: SignupComponent;
  let fixture: ComponentFixture<SignupComponent>;
  let registreService: RegistreService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule, SignupComponent], // Importando o componente standalone
      providers: [RegistreService],
    }).compileComponents();

    fixture = TestBed.createComponent(SignupComponent);
    component = fixture.componentInstance;
    registreService = TestBed.inject(RegistreService);
    router = TestBed.inject(Router);

    // Espiando a navegação para verificar se ocorre corretamente
    spyOn(router, 'navigate');
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should show success message and navigate to login on successful registration', () => {
    const name = 'Test User';
    const email = 'test@user.com';
    const password = 'password123';

    const response = { message: 'Usuário registrado com sucesso!' };

    spyOn(registreService, 'register').and.returnValue(of(response)); // Simula a resposta do serviço

    component.onSignUp(name, email, password);

    fixture.detectChanges(); // Detecta mudanças no componente

    // Verifica se a mensagem de sucesso foi exibida
    expect(component.message).toBe('Usuário registrado com sucesso!');
    expect(component.messageType).toBe('success');

    // Verifica se a navegação ocorreu para a tela de login
    setTimeout(() => {
      expect(router.navigate).toHaveBeenCalledWith(['/login']);
    }, 2000);
  });

  it('should show error message on registration failure', () => {
    const name = 'Test User';
    const email = 'test@user.com';
    const password = 'password123';

    spyOn(registreService, 'register').and.returnValue(throwError('Erro no cadastro'));

    component.onSignUp(name, email, password);

    fixture.detectChanges();

    expect(component.message).toBe('Falha no cadastro! Tente novamente.');
    expect(component.messageType).toBe('error');
  });

  it('should show warning message when fields are empty', () => {
    component.onSignUp('', '', '');

    fixture.detectChanges();

    expect(component.message).toBe('Por favor, preencha todos os campos!');
    expect(component.messageType).toBe('warning');
  });
});

