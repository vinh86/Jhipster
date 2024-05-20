import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Jhipster from './jhipster';
import JhipsterDetail from './jhipster-detail';
import JhipsterUpdate from './jhipster-update';
import JhipsterDeleteDialog from './jhipster-delete-dialog';

const JhipsterRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Jhipster />} />
    <Route path="new" element={<JhipsterUpdate />} />
    <Route path=":id">
      <Route index element={<JhipsterDetail />} />
      <Route path="edit" element={<JhipsterUpdate />} />
      <Route path="delete" element={<JhipsterDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default JhipsterRoutes;
