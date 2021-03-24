import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GiftItem from './gift-item';
import GiftItemDetail from './gift-item-detail';
import GiftItemUpdate from './gift-item-update';
import GiftItemDeleteDialog from './gift-item-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GiftItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GiftItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GiftItemDetail} />
      <ErrorBoundaryRoute path={match.url} component={GiftItem} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GiftItemDeleteDialog} />
  </>
);

export default Routes;
