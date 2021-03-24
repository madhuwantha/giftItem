import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/category">
      Category
    </MenuItem>
    <MenuItem icon="asterisk" to="/gift-item">
      Gift Item
    </MenuItem>
    <MenuItem icon="asterisk" to="/image">
      Image
    </MenuItem>
    <MenuItem icon="asterisk" to="/iinventory">
      Iinventory
    </MenuItem>
    <MenuItem icon="asterisk" to="/order">
      Order
    </MenuItem>
    <MenuItem icon="asterisk" to="/inventory">
      Inventory
    </MenuItem>
    <MenuItem icon="asterisk" to="/cart">
      Cart
    </MenuItem>
    <MenuItem icon="asterisk" to="/employee">
      Employee
    </MenuItem>
    <MenuItem icon="asterisk" to="/client">
      Client
    </MenuItem>
    <MenuItem icon="asterisk" to="/gift-order">
      Gift Order
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
