import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Iinventory from './iinventory';
import IinventoryDetail from './iinventory-detail';
import IinventoryUpdate from './iinventory-update';
import IinventoryDeleteDialog from './iinventory-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={IinventoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={IinventoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={IinventoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={Iinventory} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={IinventoryDeleteDialog} />
  </>
);

export default Routes;
