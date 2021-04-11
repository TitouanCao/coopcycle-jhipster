import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISystemePaiement } from 'app/shared/model/systeme-paiement.model';

type EntityResponseType = HttpResponse<ISystemePaiement>;
type EntityArrayResponseType = HttpResponse<ISystemePaiement[]>;

@Injectable({ providedIn: 'root' })
export class SystemePaiementService {
  public resourceUrl = SERVER_API_URL + 'api/systeme-paiements';

  constructor(protected http: HttpClient) {}

  create(systemePaiement: ISystemePaiement): Observable<EntityResponseType> {
    return this.http.post<ISystemePaiement>(this.resourceUrl, systemePaiement, { observe: 'response' });
  }

  update(systemePaiement: ISystemePaiement): Observable<EntityResponseType> {
    return this.http.put<ISystemePaiement>(this.resourceUrl, systemePaiement, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISystemePaiement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISystemePaiement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
