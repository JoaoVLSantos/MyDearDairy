import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RegistreService } from './registeService';
import { API_URL } from '../utils/constants';  // Certifique-se de que o caminho para o API_URL está correto

describe('RegistreService', () => {
  let service: RegistreService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [RegistreService],
    });
    service = TestBed.inject(RegistreService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should register a user and return success response', () => {
    const userData = { name: 'Test User', email: 'test@user.com', password: 'password' };
    const mockResponse = { message: 'Usuário registrado com sucesso!' };

    // Chama o método register do serviço
    service.register(userData.name, userData.email, userData.password).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    // Verifica se a requisição HTTP foi feita corretamente
    const req = httpMock.expectOne(`${API_URL}/users/register`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(userData);

    // Simula a resposta da API
    req.flush(mockResponse);
  });

  afterEach(() => {
    httpMock.verify();  // Verifica se não há requisições pendentes
  });
});
