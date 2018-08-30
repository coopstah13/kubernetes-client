/**
 * Copyright (C) 2015 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fabric8.kubernetes.client.mock;

import io.fabric8.kubernetes.api.model.rbac.KubernetesClusterRole;
import io.fabric8.kubernetes.api.model.rbac.KubernetesClusterRoleBuilder;
import io.fabric8.kubernetes.api.model.rbac.KubernetesClusterRoleList;
import io.fabric8.kubernetes.api.model.rbac.KubernetesPolicyRuleBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.server.mock.KubernetesServer;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class KubernetesClusterRoleCrudTest {

  private static final Logger logger = LoggerFactory.getLogger(KubernetesClusterRoleCrudTest.class);

  @Rule
  public KubernetesServer kubernetesServer = new KubernetesServer(true,true);

  @Test
  public void crudTest(){

    KubernetesClient client = kubernetesServer.getClient();

    KubernetesClusterRole kubernetesClusterRole = new KubernetesClusterRoleBuilder()
      .withNewMetadata()
        .withName("node-reader")
      .endMetadata()
      .addToRules(0, new KubernetesPolicyRuleBuilder()
        .addToApiGroups(0,"")
        .addToNonResourceURLs(0,"/healthz")
        .addToResourceNames(0,"my-node")
        .addToResources(0,"nodes")
        .addToVerbs(0, "get")
        .addToVerbs(1, "watch")
        .addToVerbs(2, "list")
        .build()
      )
      .build();

    //test of creation
    kubernetesClusterRole = client.rbac().kubernetesClusterRoles().create(kubernetesClusterRole);

    assertNotNull(kubernetesClusterRole);
    assertEquals("ClusterRole", kubernetesClusterRole.getKind());
    assertEquals("rbac.authorization.k8s.io/v1", kubernetesClusterRole.getApiVersion());
    assertNotNull(kubernetesClusterRole.getMetadata());
    assertEquals("node-reader", kubernetesClusterRole.getMetadata().getName());
    assertNotNull(kubernetesClusterRole.getRules());
    assertEquals(1, kubernetesClusterRole.getRules().size());
    assertNotNull(kubernetesClusterRole.getRules().get(0).getApiGroups());
    assertEquals(1, kubernetesClusterRole.getRules().get(0).getApiGroups().size());
    assertEquals("", kubernetesClusterRole.getRules().get(0).getApiGroups().get(0));
    assertNotNull(kubernetesClusterRole.getRules().get(0).getNonResourceURLs());
    assertEquals(1, kubernetesClusterRole.getRules().get(0).getNonResourceURLs().size());
    assertEquals("/healthz", kubernetesClusterRole.getRules().get(0).getNonResourceURLs().get(0));
    assertNotNull(kubernetesClusterRole.getRules().get(0).getResourceNames());
    assertEquals(1, kubernetesClusterRole.getRules().get(0).getResourceNames().size());
    assertEquals("my-node", kubernetesClusterRole.getRules().get(0).getResourceNames().get(0));
    assertNotNull(kubernetesClusterRole.getRules().get(0).getResources());
    assertEquals(1, kubernetesClusterRole.getRules().get(0).getResources().size());
    assertEquals("nodes", kubernetesClusterRole.getRules().get(0).getResources().get(0));
    assertNotNull(kubernetesClusterRole.getRules().get(0).getVerbs());
    assertEquals(3, kubernetesClusterRole.getRules().get(0).getVerbs().size());
    assertEquals("get", kubernetesClusterRole.getRules().get(0).getVerbs().get(0));
    assertEquals("watch", kubernetesClusterRole.getRules().get(0).getVerbs().get(1));
    assertEquals("list", kubernetesClusterRole.getRules().get(0).getVerbs().get(2));

    //test of list
    KubernetesClusterRoleList kubernetesClusterRoleList = client.rbac().kubernetesClusterRoles().list();

    assertNotNull(kubernetesClusterRoleList);
    assertNotNull(kubernetesClusterRoleList.getItems());
    assertEquals(1, kubernetesClusterRoleList.getItems().size());
    assertNotNull(kubernetesClusterRoleList.getItems().get(0));
    assertEquals("ClusterRole", kubernetesClusterRoleList.getItems().get(0).getKind());
    assertEquals("rbac.authorization.k8s.io/v1", kubernetesClusterRoleList.getItems().get(0).getApiVersion());
    assertNotNull(kubernetesClusterRoleList.getItems().get(0).getMetadata());
    assertEquals("node-reader", kubernetesClusterRoleList.getItems().get(0).getMetadata().getName());
    assertNotNull(kubernetesClusterRoleList.getItems().get(0).getRules());
    assertEquals(1, kubernetesClusterRoleList.getItems().get(0).getRules().size());
    assertNotNull(kubernetesClusterRoleList.getItems().get(0).getRules().get(0).getApiGroups());
    assertEquals(1, kubernetesClusterRoleList.getItems().get(0).getRules().get(0).getApiGroups().size());
    assertEquals("", kubernetesClusterRoleList.getItems().get(0).getRules().get(0).getApiGroups().get(0));
    assertNotNull(kubernetesClusterRoleList.getItems().get(0).getRules().get(0).getNonResourceURLs());
    assertEquals(1, kubernetesClusterRoleList.getItems().get(0).getRules().get(0).getNonResourceURLs().size());
    assertEquals("/healthz", kubernetesClusterRoleList.getItems().get(0).getRules().get(0).getNonResourceURLs().get(0));
    assertNotNull(kubernetesClusterRoleList.getItems().get(0).getRules().get(0).getResourceNames());
    assertEquals(1, kubernetesClusterRoleList.getItems().get(0).getRules().get(0).getResourceNames().size());
    assertEquals("my-node", kubernetesClusterRoleList.getItems().get(0).getRules().get(0).getResourceNames().get(0));
    assertNotNull(kubernetesClusterRoleList.getItems().get(0).getRules().get(0).getResources());
    assertEquals(1, kubernetesClusterRoleList.getItems().get(0).getRules().get(0).getResources().size());
    assertEquals("nodes", kubernetesClusterRoleList.getItems().get(0).getRules().get(0).getResources().get(0));
    assertNotNull(kubernetesClusterRoleList.getItems().get(0).getRules().get(0).getVerbs());
    assertEquals(3, kubernetesClusterRoleList.getItems().get(0).getRules().get(0).getVerbs().size());
    assertEquals("get", kubernetesClusterRoleList.getItems().get(0).getRules().get(0).getVerbs().get(0));
    assertEquals("watch", kubernetesClusterRoleList.getItems().get(0).getRules().get(0).getVerbs().get(1));
    assertEquals("list", kubernetesClusterRoleList.getItems().get(0).getRules().get(0).getVerbs().get(2));

    //test of updation

    kubernetesClusterRole = client.rbac().kubernetesClusterRoles().withName("node-reader").edit()
      .editRule(0).addToApiGroups(1, "extensions").endRule().done();

    assertNotNull(kubernetesClusterRole);
    assertEquals("ClusterRole", kubernetesClusterRole.getKind());
    assertEquals("rbac.authorization.k8s.io/v1", kubernetesClusterRole.getApiVersion());
    assertNotNull(kubernetesClusterRole.getMetadata());
    assertEquals("node-reader", kubernetesClusterRole.getMetadata().getName());
    assertNotNull(kubernetesClusterRole.getRules());
    assertEquals(1, kubernetesClusterRole.getRules().size());
    assertNotNull(kubernetesClusterRole.getRules().get(0).getApiGroups());
    assertEquals(2, kubernetesClusterRole.getRules().get(0).getApiGroups().size());
    assertEquals("", kubernetesClusterRole.getRules().get(0).getApiGroups().get(0));
    assertEquals("extensions", kubernetesClusterRole.getRules().get(0).getApiGroups().get(1));
    assertNotNull(kubernetesClusterRole.getRules().get(0).getNonResourceURLs());
    assertEquals(1, kubernetesClusterRole.getRules().get(0).getNonResourceURLs().size());
    assertEquals("/healthz", kubernetesClusterRole.getRules().get(0).getNonResourceURLs().get(0));
    assertNotNull(kubernetesClusterRole.getRules().get(0).getResourceNames());
    assertEquals(1, kubernetesClusterRole.getRules().get(0).getResourceNames().size());
    assertEquals("my-node", kubernetesClusterRole.getRules().get(0).getResourceNames().get(0));
    assertNotNull(kubernetesClusterRole.getRules().get(0).getResources());
    assertEquals(1, kubernetesClusterRole.getRules().get(0).getResources().size());
    assertEquals("nodes", kubernetesClusterRole.getRules().get(0).getResources().get(0));
    assertNotNull(kubernetesClusterRole.getRules().get(0).getVerbs());
    assertEquals(3, kubernetesClusterRole.getRules().get(0).getVerbs().size());
    assertEquals("get", kubernetesClusterRole.getRules().get(0).getVerbs().get(0));
    assertEquals("watch", kubernetesClusterRole.getRules().get(0).getVerbs().get(1));
    assertEquals("list", kubernetesClusterRole.getRules().get(0).getVerbs().get(2));

    //test of deletion
    boolean deleted = client.rbac().kubernetesClusterRoles().delete();

    assertTrue(deleted);
    kubernetesClusterRoleList = client.rbac().kubernetesClusterRoles().list();
    assertEquals(0,kubernetesClusterRoleList.getItems().size());
  }
}