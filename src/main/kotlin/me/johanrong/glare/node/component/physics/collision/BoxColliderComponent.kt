package me.johanrong.glare.node.component.physics.collision

class BoxColliderComponent : ColliderComponent() {
    override val colliderType = Collider.BOX
    override var contacts: MutableList<ColliderComponent> = mutableListOf()

    /**
     * Muligens bruke noen innebygde .obj modeller for å brukes i collisjon
     * Kanskje ferdigimportert med bare rå data.
     * BoxCollider må kunne skaleres i alle dimensjoner til å bli rektangulær og hele pakken
     * Muligens rotasjon også
     */

    override fun colliding(other: ColliderComponent): Boolean {
        return false
    }
}