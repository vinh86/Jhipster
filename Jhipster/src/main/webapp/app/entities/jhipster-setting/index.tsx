import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import JhipsterSetting from './jhipster-setting';
import JhipsterSettingDetail from './jhipster-setting-detail';
import JhipsterSettingUpdate from './jhipster-setting-update';
import JhipsterSettingDeleteDialog from './jhipster-setting-delete-dialog';

const JhipsterSettingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<JhipsterSetting />} />
    <Route path="new" element={<JhipsterSettingUpdate />} />
    <Route path=":id">
      <Route index element={<JhipsterSettingDetail />} />
      <Route path="edit" element={<JhipsterSettingUpdate />} />
      <Route path="delete" element={<JhipsterSettingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default JhipsterSettingRoutes;
